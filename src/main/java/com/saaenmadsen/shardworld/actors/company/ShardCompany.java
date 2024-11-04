package com.saaenmadsen.shardworld.actors.company;

import akka.actor.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.saaenmadsen.shardworld.actors.company.direction.DailyDirectionMeeting;
import com.saaenmadsen.shardworld.actors.company.direction.ProductionPlanning;
import com.saaenmadsen.shardworld.actors.company.direction.StrategicBoardMeeting;
import com.saaenmadsen.shardworld.actors.company.direction.TacticalBoardMeeting;
import com.saaenmadsen.shardworld.actors.countrymarket.C_BuyOrder;
import com.saaenmadsen.shardworld.actors.countrymarket.C_EndMarketDay;
import com.saaenmadsen.shardworld.actors.countrymarket.C_SendSkuToMarketForSale;
import com.saaenmadsen.shardworld.actors.shardcountry.C_CompanyDayEnd;
import com.saaenmadsen.shardworld.actors.shardcountry.CountryMainActor;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import com.saaenmadsen.shardworld.recipechoice.ProductionImpactReport;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;
import com.saaenmadsen.shardworld.statistics.CompanyDayStats;

import java.util.ArrayList;
import java.util.Optional;

public class ShardCompany extends AbstractBehavior<ShardCompany.ShardCompanyCommand> {
    private String companyId;
    private final akka.actor.typed.ActorRef<CountryMainActor.CountryMainActorCommand> countryActor;
    private final WorldSettings worldSettings;
    DailyReport dailyReport = new DailyReport();

    private CompanyInformation companyInformation;


    public interface ShardCompanyCommand {
    }

    public static Behavior<ShardCompanyCommand> create(String companyName, akka.actor.typed.ActorRef<CountryMainActor.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {

        return Behaviors.setup(context -> new ShardCompany(context, companyName, countryActor, worldSettings));
    }

    public ShardCompany(ActorContext<ShardCompanyCommand> context, String companyId, akka.actor.typed.ActorRef<CountryMainActor.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {
        super(context);
        getContext().getLog().debug(companyId + "Constructor start");
        this.companyId = companyId;
        this.countryActor = countryActor;
        this.worldSettings = worldSettings;
        this.companyInformation = new CompanyInformation(companyId);
        getContext().getLog().debug(companyId + "Constructor done");
    }


    @Override
    public Receive<ShardCompanyCommand> createReceive() {
        getContext().getLog().debug("ShardCompany createReceive");
        return newReceiveBuilder()
                .onMessage(C_MarketOpenForSellers.class, this::onReceiveMarketOpenForSellers)
                .onMessage(C_CompletedBuyOrder.class, this::onReceiveCompletedBuyOrder)
                .onMessage(C_MarketOpenForBuyers.class, this::onReceiveMarketOpenForBuyers)
                .onMessage(C_SendUnsoldSkuBackToSeller.class, this::onSendUnsoldSkuBackToSeller)
                .build();
    }

    // ****************** Acions *************************

    private Behavior<ShardCompanyCommand> onReceiveMarketOpenForSellers(C_MarketOpenForSellers message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info(companyId + " got message {}", message.toString());
        }
        dailyReport = new DailyReport();
        ActorRef parent = Adapter.toClassic(getContext()).parent();
        companyInformation.setPriceList(message.priceList());
        new ProductionPlanning(companyInformation, dailyReport);
        doProduction(message.priceList());
        sendSkuItemsForSaleToMarket(message);

        return Behaviors.same();
    }

    private void doProduction(PriceList priceList) {
        RecipeChoiceReport toWorkRecipe = RecipeChoiceReport.findRecipeWithHighestProjectedProfit(companyInformation, priceList);
        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : toWorkRecipe.productionChoices()) {
            String message = companyId + " production " + productionChoice.recipe().name() + " a total of " + productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials() + " times.";
            getContext().getLog().debug(message);
            productionChoice.recipe().runProduction(productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials(), companyInformation.getWarehouse());
            dailyReport.appendToDailyReport(message);
        }
    }


    private void sendSkuItemsForSaleToMarket(C_MarketOpenForSellers message) {
        // For now, just setting everything for sale.
        StockListing forSaleList = companyInformation.getWarehouse().retrieve(companyInformation.getWarehouse());
        this.dailyReport.setForSaleList(forSaleList);
        message.countryMarket().tell(new C_SendSkuToMarketForSale(forSaleList, getContext().getSelf()));
    }

    private Behavior<ShardCompanyCommand> onReceiveMarketOpenForBuyers(C_MarketOpenForBuyers message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info(companyId + " got message {}", message.toString());
        }
        companyInformation.setPriceList(message.priceList());
        ArrayList<KnownRecipe> prepareToProduceRecipies = getListOfTwoMostProfitableRecipes(message);
        StockListing buyList = buildBuyList(prepareToProduceRecipies, companyInformation.calculateWorkTimeAvailable());

        message.countryMarket().tell(new C_BuyOrder(buyList, getContext().getSelf()));
        return Behaviors.same();
    }

    public static StockListing buildBuyList(ArrayList<KnownRecipe> prepareToProduceRecipies, int workTimeAvailable) {
        StockListing buyList = StockListing.createEmptyStockListing();

        for (KnownRecipe knownRecipe : prepareToProduceRecipies) {
            ProductionImpactReport evaluation = knownRecipe.getRecipe().evaluateRawMaterialImpact(workTimeAvailable, StockListing.createMaxedOutStockListing());
            buyList.addStockFromList(evaluation.usedRawMaterial());
        }
        return buyList;
    }

    private ArrayList<KnownRecipe> getListOfTwoMostProfitableRecipes(C_MarketOpenForBuyers message) {
        Optional<KnownRecipe> prepare1 = Optional.empty();
        Optional<KnownRecipe> prepare2 = Optional.empty();

        for (KnownRecipe myRecipe : companyInformation.getKnownRecipes()) {
            myRecipe.setProfitability(myRecipe.recipe().calculateProfitPrWorkTenMin(message.priceList()));
            if (prepare1.isEmpty()) {
                prepare1 = Optional.of(myRecipe);
            } else {
                if (prepare2.isEmpty()) {
                    prepare2 = Optional.of(myRecipe);
                } else {
                    if (prepare1.get().getProfitability() < myRecipe.getProfitability()) {
                        prepare1 = Optional.of(myRecipe);
                    } else {
                        if (prepare2.get().getProfitability() < myRecipe.getProfitability()) {
                            prepare2 = Optional.of(myRecipe);
                        }
                    }
                }
            }
        }
        ArrayList<KnownRecipe> result = new ArrayList<>();
        if (prepare1.isPresent()) {
            result.add(prepare1.get());
        }
        if (prepare2.isPresent()) {
            result.add(prepare2.get());
        }
        return result;
    }


    private Behavior<ShardCompanyCommand> onReceiveCompletedBuyOrder(C_CompletedBuyOrder message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info(companyId + " got message {}", message.toString());
        }

        return Behaviors.same();
    }

    private Behavior<ShardCompanyCommand> onSendUnsoldSkuBackToSeller(C_SendUnsoldSkuBackToSeller message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info(companyId + " got message {}", message.toString());
        }
        dailyReport.setUnsoldGoods(message.unsoldGoods());
        companyInformation.getWarehouse().addStockFromList(message.unsoldGoods());

        new DailyDirectionMeeting(companyInformation, dailyReport);

        if (companyInformation.timeForTacticalBoardMeeting()) {
            new TacticalBoardMeeting(companyInformation, dailyReport);
        }
        if (companyInformation.timeForStrategicBoardMeeting()) {
            new StrategicBoardMeeting(companyInformation, dailyReport);
        }
        message.market().tell(new C_EndMarketDay(this.companyId));
        countryActor.tell(new C_CompanyDayEnd(new CompanyDayStats(dailyReport.toString(), companyInformation.getKnownRecipes(), dailyReport.getUnsoldGoods(), companyInformation.getWarehouse().createDuplicate())));
        return Behaviors.same();
    }
}

