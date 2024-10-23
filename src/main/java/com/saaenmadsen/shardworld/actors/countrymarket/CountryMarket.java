package com.saaenmadsen.shardworld.actors.countrymarket;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.Random;


public class CountryMarket extends AbstractBehavior<CountryMarket.CountryMarketCommand> {
    protected static Random dice = new Random();
    protected static int maxMillisecondsCookTime = 200;

    public interface CountryMarketCommand {}

    public static Behavior<CountryMarketCommand> create() {
        return Behaviors.setup(CountryMarket::new);
    }

    public CountryMarket(ActorContext<CountryMarketCommand> context) {
        super(context);
    }

    static final Behavior<CountryMarketCommand> create(ActorSystem<CountryMarketCommand> kitchenOutbox) {
        return Behaviors.setup(CountryMarket::new);
    }

    @Override
    public Receive<CountryMarketCommand> createReceive() {
        getContext().getLog().info("CountryMarket createReceive");
        return newReceiveBuilder().onMessage(CountryMarketCommand.class, this::onReceiveMenuOrder).build();
    }

    private Behavior<CountryMarketCommand> onReceiveMenuOrder(CountryMarketCommand mealCommand) {
        getContext().getLog().info("Got new message to prepare {}", mealCommand.toString());
        takingMyTimeCooking();
        return Behaviors.same();
    }


    public static void takingMyTimeCooking(){
        try {
            Thread.sleep(dice.nextLong(maxMillisecondsCookTime));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

