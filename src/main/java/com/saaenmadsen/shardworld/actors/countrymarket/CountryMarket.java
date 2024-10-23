package com.saaenmadsen.shardworld.actors.countrymarket;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.company.C_MarketOpenForSellers;
import com.saaenmadsen.shardworld.modeltypes.PriceList;

import java.util.Random;


public class CountryMarket extends AbstractBehavior<CountryMarket.CountryMarketCommand> {
    protected static Random dice = new Random();
    protected static int maxMillisecondsCookTime = 200;

    private PriceList priceList;

    public interface CountryMarketCommand {}

    public static Behavior<CountryMarketCommand> create() {
        return Behaviors.setup(CountryMarket::new);
    }

    public CountryMarket(ActorContext<CountryMarketCommand> context) {
        super(context);
        priceList = new PriceList();
    }

    static final Behavior<CountryMarketCommand> create(ActorSystem<CountryMarketCommand> kitchenOutbox) {
        return Behaviors.setup(CountryMarket::new);
    }

    @Override
    public Receive<CountryMarketCommand> createReceive() {
        getContext().getLog().info("CountryMarket createReceive");
        return newReceiveBuilder()
                .onMessage(C_BuyOrder.class, this::onReceiveBuyOrder)
                .onMessage(C_EndMarketDay.class, this::onReceiveEndMarketDay)
                .onMessage(C_SendSkuToMarketForSale.class, this::onReceiveSendSkuToMarketForSale)
                .onMessage(C_StartMarketDayCycle.class, this::onReceiveStartMarketDayCycle)
                .build();
    }

    private Behavior<CountryMarketCommand> onReceiveBuyOrder(C_BuyOrder message) {
        getContext().getLog().info("Market got message {}", message.toString());
        return Behaviors.same();
    }
    private Behavior<CountryMarketCommand> onReceiveEndMarketDay(C_EndMarketDay message) {
        getContext().getLog().info("Market got message {}", message.toString());
        return Behaviors.same();
    }
    private Behavior<CountryMarketCommand> onReceiveSendSkuToMarketForSale(C_SendSkuToMarketForSale message) {
        getContext().getLog().info("Market got message {}", message.toString());
        return Behaviors.same();
    }
    private Behavior<CountryMarketCommand> onReceiveStartMarketDayCycle(C_StartMarketDayCycle message) {
        getContext().getLog().info("Market got message {}", message.toString());
        message.allCompanies().stream().forEach(c->c.tell(new C_MarketOpenForSellers(message.dayId(), priceList, getContext().getSelf())));
        return Behaviors.same();
    }


}

