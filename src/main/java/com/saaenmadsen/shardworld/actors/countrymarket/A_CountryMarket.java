package com.saaenmadsen.shardworld.actors.countrymarket;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.company.*;
import com.saaenmadsen.shardworld.actors.shardcountry.A_ShardCountry;
import com.saaenmadsen.shardworld.actors.shardcountry.C_EndMarketDayCycle;
import com.saaenmadsen.shardworld.constants.WorldSettings;
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
                .onMessage(C_BuyOrder.class, this::onReceiveBuyOrder)
                .onMessage(C_EndMarketDay.class, this::onReceiveEndMarketDay)
                .build();
    }

    private Behavior<CountryMarketCommand> onReceiveStartMarketDayCycle(C_StartMarketDayCycle message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Market got message {}", message.toString());
        }
        marketDay = new MarketDay(marketDay);

        this.allCompanies = message.allCompanies();
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
            allCompanies.forEach(c -> c.tell(new C_MarketOpenForBuyers(dayId, marketDay.getNewestPriceList(), getContext().getSelf())));
        }
        return Behaviors.same();
    }

    private Behavior<CountryMarketCommand> onReceiveBuyOrder(C_BuyOrder message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Market got message {}", message.toString());
        }
        marketDay.buyOrderList.add(message);
        MoneyBox buyersMoney = message.moneyBox();
        getContext().getLog().info("Market buyer {} money is {}", message.buyerCompanyId(), message.moneyBox().getMoney());

        message.buyer().tell(
                new C_CompletedBuyOrder(
                        marketDay.doShoppingAndReturnShoppingCart(message.wishList(), buyersMoney),
                        buyersMoney
                )
        );

        if (marketDay.buyOrderList.size() >= allCompanies.size()) {
            // This could be a state shift :)

            // Send unsold stuff back to the sellers.
            marketDay.marketBooths.forEach(booth -> booth.getSeller().tell(
                            new C_SendUnsoldSkuBackToSeller(
                                    booth.closeBoothAndGetRemainingForSaleList(),
                                    booth.getBoothRevenue(),
                                    getContext().getSelf())
                    )
            );
        }
        return Behaviors.same();
    }

    private Behavior<CountryMarketCommand> onReceiveEndMarketDay(C_EndMarketDay message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Market got message {}", message.toString());
        }
        marketDay.companiesDoneWithMarketDay++;
        if (marketDay.companiesDoneWithMarketDay == allCompanies.size()) {
            country.tell(new C_EndMarketDayCycle(dayId, new MarketDayStats(marketDay.getPriceListDayStart().duplicate(), marketDay.getNewestPriceList().duplicate())));
        }
        return Behaviors.same();
    }


}

