package com.saaenmadsen.shardworld.actors.company;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.saaenmadsen.shardworld.actors.company.direction.*;
import com.saaenmadsen.shardworld.actors.countrymarket.C_BuyOrder;
import com.saaenmadsen.shardworld.actors.countrymarket.C_EndMarketDay;
import com.saaenmadsen.shardworld.actors.countrymarket.C_SendSkuToMarketForSale;
import com.saaenmadsen.shardworld.actors.shardcountry.C_CompanyDayEnd;
import com.saaenmadsen.shardworld.actors.shardcountry.A_ShardCountry;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettings;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import com.saaenmadsen.shardworld.statistics.CompanyDayStats;

public class A_ShardCompany extends AbstractBehavior<A_ShardCompany.ShardCompanyCommand> {
    private String companyId;
    private final akka.actor.typed.ActorRef<A_ShardCountry.CountryMainActorCommand> countryActor;
    private final WorldSettings worldSettings;
    CompanyDailyReport companyDailyReport = new CompanyDailyReport(companyId, 1);


    private CompanyInformation companyInformation;




    public interface ShardCompanyCommand {
    }

    public static Behavior<ShardCompanyCommand> create(String companyName, akka.actor.typed.ActorRef<A_ShardCountry.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {
        return Behaviors.setup(context -> new A_ShardCompany(context, companyName, countryActor, worldSettings));
    }

    public static Behavior<ShardCompanyCommand> create(CompanyInformation companyInformation, akka.actor.typed.ActorRef<A_ShardCountry.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {
        return Behaviors.setup(context -> new A_ShardCompany(context, companyInformation, countryActor, worldSettings));
    }

    public A_ShardCompany(ActorContext<ShardCompanyCommand> context, CompanyInformation companyInformation, ActorRef<A_ShardCountry.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {
        super(context);
        getContext().getLog().debug(companyId + "Constructor start");
        this.companyId = companyInformation.getCompanyId();
        this.countryActor = countryActor;
        this.worldSettings = worldSettings;
        this.companyInformation = companyInformation;
        getContext().getLog().debug("After constructor: " + companyId + " got money {}", companyInformation.getMoneyBox().getMoney());
        getContext().getLog().debug(companyId + "Constructor done");
    }

    public A_ShardCompany(ActorContext<ShardCompanyCommand> context, String companyId, akka.actor.typed.ActorRef<A_ShardCountry.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {
        super(context);
        getContext().getLog().debug(companyId + "Constructor start");
        this.companyId = companyId;
        this.countryActor = countryActor;
        this.worldSettings = worldSettings;
        this.companyInformation = CompanyInformationBuilder.ofWorldDefault(companyId, worldSettings).build();
        this.companyDailyReport = new CompanyDailyReport(companyId, 0);
        new FoundingBoardMeeting(companyInformation, companyDailyReport);
        getContext().getLog().debug("After constructor: " + companyId + " got money {}", companyInformation.getMoneyBox().getMoney());
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
        getContext().getLog().debug("onReceiveMarketOpenForSellers: " + companyId + " got money {}", companyInformation.getMoneyBox().getMoney());

        companyDailyReport = new CompanyDailyReport(companyId, message.dayId());
        companyInformation.setPriceList(message.priceList());

        new ProductionPlanningAndExecution(companyInformation, companyDailyReport);

        StockListing forSaleList = new DesiceWhatToSellAtMarket(companyInformation, companyDailyReport).getForSaleList();
        message.countryMarket().tell(new C_SendSkuToMarketForSale(forSaleList, getContext().getSelf()));

        return Behaviors.same();
    }

    private Behavior<ShardCompanyCommand> onReceiveMarketOpenForBuyers(C_MarketOpenForBuyers message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info(companyId + " got message {}", message.toString());
        }
        getContext().getLog().debug("onReceiveMarketOpenForBuyers: " + companyId + " got money {}", companyInformation.getMoneyBox().getMoney());
        companyInformation.setPriceList(message.priceList());

        StockListing shoppingList = new DesideWhatToBuyAtMarket(companyInformation, companyDailyReport).decide();

        message.countryMarket().tell(
                new C_BuyOrder(
                        shoppingList,
                        companyInformation.getMoneyBox().newBoxWithAllTheMoeny(),
                        getContext().getSelf(),
                        this.companyId
                )
        );
        return Behaviors.same();
    }




    private Behavior<ShardCompanyCommand> onReceiveCompletedBuyOrder(C_CompletedBuyOrder message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info(companyId + " got message {}", message.toString());
        }

        this.companyInformation.getMoneyBox().addMoney(message.unspentMoney().getMoney());
        this.companyInformation.getWarehouse().addStockFromList(message.purchasedGoods());

        return Behaviors.same();
    }

    private Behavior<ShardCompanyCommand> onSendUnsoldSkuBackToSeller(C_SendUnsoldSkuBackToSeller message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info(companyId + " got message {}", message.toString());
        }

        companyDailyReport.setUnsoldGoods(message.unsoldGoods());
        companyDailyReport.setMarketDayRevenue(message.boothRevenue().getMoney());

        companyInformation.getWarehouse().addStockFromList(message.unsoldGoods());
        companyInformation.getMoneyBox().addMoney(message.boothRevenue().getMoney());

        companyDailyReport.setLiquidityDayEnd(this.companyInformation.getMoneyBox().getMoney());

        new DayEndEvaluationDirectionMeeting(companyInformation, companyDailyReport);

        if (companyInformation.timeForTacticalBoardMeeting()) {
            new InnovateOurRecipesBoardMeeting(companyInformation, companyDailyReport, message.unfulfilledOrders());
        }
        message.market().tell(new C_EndMarketDay(this.companyId));
        countryActor.tell(new C_CompanyDayEnd(new CompanyDayStats(companyDailyReport, companyInformation.getKnownRecipes(), companyDailyReport.getUnsoldGoods(), companyInformation.getWarehouse().createDuplicate())));
        return Behaviors.same();
    }
}

