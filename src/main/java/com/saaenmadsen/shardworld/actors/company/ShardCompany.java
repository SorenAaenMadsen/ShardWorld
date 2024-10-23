package com.saaenmadsen.shardworld.actors.company;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.countrymarket.C_SellOrder;

import java.util.Random;


public class ShardCompany extends AbstractBehavior<C_SellOrder> {
    protected static Random dice = new Random();
    protected static int maxMillisecondsCookTime = 200;

    public static Behavior<C_SellOrder> create() {
        return Behaviors.setup(ShardCompany::new);
    }

    public ShardCompany(ActorContext<C_SellOrder> context) {
        super(context);
    }

    static final Behavior<C_SellOrder> create(ActorSystem<C_SellOrder> kitchenOutbox) {
        return Behaviors.setup(ShardCompany::new);
    }

    @Override
    public Receive<C_SellOrder> createReceive() {
        getContext().getLog().info("ShardCompany createReceive");
        return newReceiveBuilder().onMessage(C_SellOrder.class, this::onReceiveMenuOrder).build();
    }

    private Behavior<C_SellOrder> onReceiveMenuOrder(C_SellOrder mealCommand) {
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

