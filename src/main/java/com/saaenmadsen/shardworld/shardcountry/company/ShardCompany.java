package com.saaenmadsen.shardworld.shardcountry.company;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Receive;

import java.util.Random;


public class ShardCompany extends AbstractBehavior<SellCommand> {
    protected static Random dice = new Random();
    protected static int maxMillisecondsCookTime = 200;

    public static Behavior<SellCommand> create() {
        return Behaviors.setup(ShardCompany::new);
    }

    public ShardCompany(ActorContext<SellCommand> context) {
        super(context);
    }

    static final Behavior<SellCommand> create(ActorSystem<SellCommand> kitchenOutbox) {
        return Behaviors.setup(ShardCompany::new);
    }

    @Override
    public Receive<SellCommand> createReceive() {
        getContext().getLog().info("Kitchen createReceive");
        return newReceiveBuilder().onMessage(SellCommand.class, this::onReceiveMenuOrder).build();
    }

    private Behavior<SellCommand> onReceiveMenuOrder(SellCommand mealCommand) {
        getContext().getLog().info("Got new meal to prepare {}", mealCommand.toString());
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

