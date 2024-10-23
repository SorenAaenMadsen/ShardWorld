package com.saaenmadsen.shardworld.actors.company;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Receive;

import java.util.Random;


public class ShardCompany extends AbstractBehavior<ShardCompany.ShardCompanyCommand> {
    protected static Random dice = new Random();
    protected static int maxMillisecondsCookTime = 200;

    public interface ShardCompanyCommand{}
    public static Behavior<ShardCompanyCommand> create() {
        return Behaviors.setup(ShardCompany::new);
    }

    public ShardCompany(ActorContext<ShardCompanyCommand> context) {
        super(context);
    }

    static final Behavior<ShardCompanyCommand> create(ActorSystem<ShardCompanyCommand> kitchenOutbox) {
        return Behaviors.setup(ShardCompany::new);
    }

    @Override
    public Receive<ShardCompanyCommand> createReceive() {
        getContext().getLog().info("ShardCompany createReceive");
        return newReceiveBuilder().onMessage(ShardCompanyCommand.class, this::onReceiveMenuOrder).build();
    }

    private Behavior<ShardCompanyCommand> onReceiveMenuOrder(ShardCompanyCommand mealCommand) {
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

