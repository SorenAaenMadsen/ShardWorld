package com.saaenmadsen.shardworld.actors.company;

import akka.actor.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyCulture;
import com.saaenmadsen.shardworld.actors.company.flawor.CompanyFlawor;
import com.saaenmadsen.shardworld.actors.shardcountry.C_CompanyDayEnd;
import com.saaenmadsen.shardworld.actors.shardcountry.CountryMainActor;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.recipechoice.ProductionImpactReport;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;
import com.saaenmadsen.shardworld.actors.countrymarket.C_BuyOrder;
import com.saaenmadsen.shardworld.actors.countrymarket.C_EndMarketDay;
import com.saaenmadsen.shardworld.actors.countrymarket.C_SendSkuToMarketForSale;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import akka.actor.typed.javadsl.Adapter;
import com.saaenmadsen.shardworld.statistics.CompanyDayStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ShardCompany extends AbstractBehavior<ShardCompany.ShardCompanyCommand> {
    private String companyId;
    private final akka.actor.typed.ActorRef<CountryMainActor.CountryMainActorCommand> countryActor;
    private final WorldSettings worldSettings;

    private PriceList priceList;

    private StockListing reportUnsoldGoods;

    private CompanyInformation companyInformation;


    public interface ShardCompanyCommand {
    }

    public static Behavior<ShardCompanyCommand> create(String companyName, akka.actor.typed.ActorRef<CountryMainActor.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {
        return Behaviors.setup(context -> new ShardCompany(context, companyName, countryActor, worldSettings));
    }

    public ShardCompany(ActorContext<ShardCompanyCommand> context, String companyName, akka.actor.typed.ActorRef<CountryMainActor.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {
        super(context);
        this.companyId = companyName;
        this.countryActor = countryActor;
        this.worldSettings = worldSettings;
        this.companyInformation = new CompanyInformation();
        this.priceList = new PriceList();
        dailyCompanyDirectionEvaluation();
    }


    @Override
    public Receive<ShardCompanyCommand> createReceive() {
        getContext().getLog().info("ShardCompany createReceive");
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
        ActorRef parent = Adapter.toClassic(getContext()).parent();
        this.priceList = message.priceList();
        doProduction(message.priceList());
        sendSkuItemsForSaleToMarket(message);

        return Behaviors.same();
    }

    private void doProduction(PriceList priceList) {
        RecipeChoiceReport toWorkRecipe = RecipeChoiceReport.findRecipeWithHighestProjectedProfit(companyInformation, priceList);
        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : toWorkRecipe.productionChoices()) {
            String message = companyId + " production " + productionChoice.recipe().name() + " a total of " + productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials() + " times.";
            getContext().getLog().info(message);
            productionChoice.recipe().runProduction(productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials(), companyInformation.getWarehouse());
            appendToDailyReport(message);
        }
    }



    private void sendSkuItemsForSaleToMarket(C_MarketOpenForSellers message) {
        // For now, just setting everything for sale.
        StockListing forSaleList = companyInformation.getWarehouse().retrieve(companyInformation.getWarehouse());
        message.countryMarket().tell(new C_SendSkuToMarketForSale(forSaleList, getContext().getSelf()));
    }

    private Behavior<ShardCompanyCommand> onReceiveMarketOpenForBuyers(C_MarketOpenForBuyers message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info(companyId + " got message {}", message.toString());
        }
        this.priceList = message.priceList();
        ArrayList<KnownRecipe> prepareToProduceRecipies = getListOfTwoMostProfitableRecipes(message);
        StockListing buyList = buildBuyList(prepareToProduceRecipies, companyInformation.calculateWorkTimeAvailable());

        message.countryMarket().tell(new C_BuyOrder(buyList, getContext().getSelf()) );
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

        for (KnownRecipe myRecipe : companyInformation.getMyRecipes()) {
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

    StringBuilder dailyReport = new StringBuilder();

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
        this.reportUnsoldGoods = message.unsoldGoods().createDuplicate();
        companyInformation.getWarehouse().addStockFromList(message.unsoldGoods());


        dailyCompanyDirectionEvaluation();

        message.market().tell(new C_EndMarketDay(this.companyId));
        countryActor.tell(new C_CompanyDayEnd(new CompanyDayStats(dailyReport.toString(), companyInformation.getMyRecipes(), reportUnsoldGoods, companyInformation.getWarehouse().createDuplicate())));
        return Behaviors.same();
    }

    private void dailyCompanyDirectionEvaluation() {
        investInNewProductionRecipies(companyInformation.getWarehouse(), priceList);
        boolean holdBoardMeeting = true;
        if(holdBoardMeeting){
            longTermCompanyDirectionEvaluation();
        }
    }

    private void longTermCompanyDirectionEvaluation() {

    }

    private void investInNewProductionRecipies(StockListing warehouse, PriceList priceList) {
        String logMessage = companyId + " recipe adjustment: ";
        if(companyInformation.getMyRecipes().size()>5){
            logMessage += " removed " + companyInformation.getMyRecipes().getFirst().recipe().name();
            companyInformation.getMyRecipes().removeFirst();
        }
        Random dice = new Random();
        int newRecipeIndex = dice.nextInt(Recipe.values().length);
        Recipe newRecipe = Recipe.values()[newRecipeIndex];
        companyInformation.getMyRecipes().add(new KnownRecipe(newRecipe));
        logMessage += " added " + newRecipe.name();
        appendToDailyReport(logMessage);
        getContext().getLog().info(logMessage);

    }

    private void appendToDailyReport(String section){
        dailyReport.append(" | "+section);
    }


}

