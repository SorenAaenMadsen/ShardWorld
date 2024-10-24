package com.saaenmadsen.shardworld.actors.company;

import akka.actor.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.recipechoice.ProductionImpactReport;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;
import com.saaenmadsen.shardworld.actors.countrymarket.C_BuyOrder;
import com.saaenmadsen.shardworld.actors.countrymarket.C_EndMarketDay;
import com.saaenmadsen.shardworld.actors.countrymarket.C_SendSkuToMarketForSale;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import akka.actor.typed.javadsl.Adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ShardCompany extends AbstractBehavior<ShardCompany.ShardCompanyCommand> {
    private String companyName;
    private StockListing warehouse;
    private List<KnownRecipe> myRecipes = new ArrayList<>();
    private int workers = 10;
    private PriceList priceList;

    public interface ShardCompanyCommand {
    }

    public static Behavior<ShardCompanyCommand> create(String companyName) {
        return Behaviors.setup(context -> new ShardCompany(context, companyName));
    }

    public ShardCompany(ActorContext<ShardCompanyCommand> context, String companyName) {
        super(context);
        this.companyName = companyName;
        this.warehouse = StockListing.createEmptyStockListing();
        this.priceList = new PriceList();
        investInNewProductionRecipies(warehouse, priceList);
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
        getContext().getLog().info(companyName + " got message {}", message.toString());
        ActorRef parent = Adapter.toClassic(getContext()).parent();
        this.priceList = message.priceList();
        doProduction(message.priceList());
        sendSkuItemsForSaleToMarket(message);

        return Behaviors.same();
    }

    private void doProduction(PriceList priceList) {
        RecipeChoiceReport toWorkRecipe = RecipeChoiceReport.findRecipeWithHighestProjectedProfit(myRecipes, warehouse, priceList, getWorkTimeAvailable());
        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : toWorkRecipe.productionChoices()) {

            getContext().getLog().info(companyName + " production {}  ", productionChoice.recipe().name() + " a total of " + productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials() + " times.");
            productionChoice.recipe().runProduction(productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials(), warehouse);
        }
    }

    private int getWorkTimeAvailable() {
        return workers * 8;
    }

    private void sendSkuItemsForSaleToMarket(C_MarketOpenForSellers message) {
        // For now, just setting everything for sale.
        StockListing forSaleList = warehouse.retrieve(warehouse);
        message.countryMarket().tell(new C_SendSkuToMarketForSale(forSaleList, getContext().getSelf()));
    }

    private Behavior<ShardCompanyCommand> onReceiveMarketOpenForBuyers(C_MarketOpenForBuyers message) {
        getContext().getLog().info(companyName + " got message {}", message.toString());
        this.priceList = message.priceList();
        ArrayList<KnownRecipe> prepareToProduceRecipies = getListOfTwoMostProfitableRecipes(message);
        StockListing buyList = buildBuyList(prepareToProduceRecipies, getWorkTimeAvailable());

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

        for (KnownRecipe myRecipe : myRecipes) {
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
        getContext().getLog().info(companyName + " got message {}", message.toString());

        return Behaviors.same();
    }

    private Behavior<ShardCompanyCommand> onSendUnsoldSkuBackToSeller(C_SendUnsoldSkuBackToSeller message) {
        getContext().getLog().info(companyName + " got message {}", message.toString());
        warehouse.addStockFromList(message.unsoldGoods());
        investInNewProductionRecipies(warehouse, priceList);

        message.market().tell(new C_EndMarketDay(this.companyName));
        return Behaviors.same();
    }

    private void investInNewProductionRecipies(StockListing warehouse, PriceList priceList) {
        String logMessage = companyName + " recipe adjustment: ";
        if(myRecipes.size()>5){
            logMessage += " removed " + myRecipes.getFirst().recipe().name();
            myRecipes.removeFirst();
        }
        Random dice = new Random();
        int newRecipeIndex = dice.nextInt(Recipe.values().length);
        Recipe newRecipe = Recipe.values()[newRecipeIndex];
        myRecipes.add(new KnownRecipe(newRecipe));
        logMessage += " added " + newRecipe.name();
        getContext().getLog().info(logMessage);

    }


}

