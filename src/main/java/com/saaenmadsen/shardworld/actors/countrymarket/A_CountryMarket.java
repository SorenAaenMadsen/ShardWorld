package com.saaenmadsen.shardworld.actors.countrymarket;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.company.*;
import com.saaenmadsen.shardworld.actors.popgroup.A_PopGroup;
import com.saaenmadsen.shardworld.actors.popgroup.C_CompletedPopBuyOrder;
import com.saaenmadsen.shardworld.actors.popgroup.C_MarketOpenForPopBuyers;
import com.saaenmadsen.shardworld.actors.shardcountry.A_ShardCountry;
import com.saaenmadsen.shardworld.actors.shardcountry.C_EndMarketDayCycle;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettings;
import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.statistics.MarketDayStats;

import java.util.List;
import java.util.Random;


public class A_CountryMarket extends AbstractBehavior<A_CountryMarket.CountryMarketCommand> {
    protected static Random dice = new Random();
    protected static int maxMillisecondsCookTime = 200;
    private final ActorRef<A_ShardCountry.CountryMainActorCommand> country;
    private final WorldSettings worldSettings;
    private MarketDay marketDay;


    private List<ActorRef<A_ShardCompany.ShardCompanyCommand>> allCompanies;
    private List<ActorRef<A_PopGroup.PopGroupCommand>> allPopGroups;
    private int dayId;

    public interface CountryMarketCommand {
    }

    public static Behavior<CountryMarketCommand> create(ActorRef<A_ShardCountry.CountryMainActorCommand> country, WorldSettings worldSettings) {
        return Behaviors.setup(context -> new A_CountryMarket(context, country, worldSettings));
    }

    public A_CountryMarket(ActorContext<CountryMarketCommand> context, ActorRef<A_ShardCountry.CountryMainActorCommand> country, WorldSettings worldSettings) {
        super(context);
        this.worldSettings = worldSettings;
        this.country = country;
    }

    @Override
    public Receive<CountryMarketCommand> createReceive() {
        getContext().getLog().info("Market createReceive");
        return newReceiveBuilder()
                .onMessage(C_StartMarketDayCycle.class, this::onReceiveStartMarketDayCycle)
                .onMessage(C_SendSkuToMarketForSale.class, this::onReceiveSendSkuToMarketForSale)
                .onMessage(C_BuyB2BOrder.class, this::onReceiveB2BBuyOrder)
                .onMessage(C_EndB2BMarketDay.class, this::onReceiveEndMarketDay)
                .onMessage(C_BuyPopOrder.class, this::onBuyPopOrder)
                .onMessage(C_EndPopMarketDay.class, this::onEndPopMarketDay)
                .build();
    }


    private Behavior<CountryMarketCommand> onReceiveStartMarketDayCycle(C_StartMarketDayCycle message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Market got message {}", message.toString());
        }
        marketDay = new MarketDay(marketDay, message.dayId());

        this.allCompanies = message.allCompanies();
        this.allPopGroups = message.allPopGroups();
        this.dayId = message.dayId();
        message.allCompanies().forEach(c -> c.tell(new C_MarketOpenForSellers(message.dayId(), marketDay.getNewestPriceList(), getContext().getSelf())));
        return Behaviors.same();
    }

    private Behavior<CountryMarketCommand> onReceiveSendSkuToMarketForSale(C_SendSkuToMarketForSale message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Market got message {}", message.toString());
        }
        MarketBooth sellersBooth = new MarketBooth(message.forSaleList(), message.seller());
        marketDay.addBooth(sellersBooth);
        if (marketDay.marketBooths.size() >= allCompanies.size()) {
            // This could be a state shift :)
            allCompanies.forEach(c -> c.tell(new C_MarketOpenForB2BBuyers(dayId, marketDay.getNewestPriceList(), getContext().getSelf())));
            allPopGroups.forEach(c -> c.tell(new C_MarketOpenForPopBuyers(dayId, marketDay.getNewestPriceList(), getContext().getSelf())));
        }
        return Behaviors.same();
    }

    private Behavior<CountryMarketCommand> onReceiveB2BBuyOrder(C_BuyB2BOrder message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Market got message {}", message.toString());
        }
        marketDay.b2bBuyOrderList.add(message);
        MoneyBox buyersMoney = message.moneyBox();
        getContext().getLog().debug("Market buyer {} money is {}", message.buyerCompanyId(), message.moneyBox().getMoney());

        message.buyer().tell(
                new C_CompletedB2BBuyOrder(
                        marketDay.doShoppingAndReturnShoppingCart(message.wishList(), buyersMoney),
                        buyersMoney
                )
        );

        if (marketDay.enoughBuyersHaveDoneTheirBusiness(allCompanies.size(), allPopGroups.size())) {
            // All buyers have done their business:
            closeDownMarketForToday();
        }
        return Behaviors.same();
    }

    private void closeDownMarketForToday() {
        marketDay.adjustPrices(getContext().getLog());
        marketDay.marketBooths.forEach(booth -> booth.getSeller().tell(
                        new C_SendUnsoldSkuBackToSeller(
                                booth.closeBoothAndGetRemainingForSaleList(),
                                booth.getBoothRevenue(),
                                marketDay.getUnfulfilledOrders(),
                                getContext().getSelf())
                )
        );
    }

    private Behavior<CountryMarketCommand> onReceiveEndMarketDay(C_EndB2BMarketDay message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Company is home from market safely {}", message.toString());
        }
        marketDay.companiesDoneWithMarketDay++;
        if (marketDay.companiesDoneWithMarketDay == allCompanies.size()) {
            getContext().getLog().debug("Market can now close. All sellers and buyers are home safely. {}", message.toString());
            country.tell(new C_EndMarketDayCycle(dayId, new MarketDayStats(marketDay.createMarketDayClosedownReport())));
        }
        return Behaviors.same();
    }

    private Behavior<CountryMarketCommand> onBuyPopOrder(C_BuyPopOrder message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Pop buy order is in {}", message.toString());
        }
        marketDay.popBuyOrderList.add(message);
        MoneyBox buyersMoney = message.moneyBox();
        getContext().getLog().debug("Market pop buyer {} money is {}", message.popGroupName(), message.moneyBox().getMoney());

        message.buyer().tell(
                new C_CompletedPopBuyOrder(
                        marketDay.doShoppingAndReturnShoppingCart(message.wishList(), buyersMoney),
                        buyersMoney
                )
        );

        if (marketDay.enoughBuyersHaveDoneTheirBusiness(allCompanies.size(), allPopGroups.size())) {
            // All buyers have done their business:
            closeDownMarketForToday();
        }
        return Behaviors.same();
    }

    private Behavior<CountryMarketCommand> onEndPopMarketDay(C_EndPopMarketDay message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Pop marked day complete {}", message.toString());
        }
        return Behaviors.same();
    }
}

