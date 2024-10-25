package com.saaenmadsen.shardworld.actors.shardworld;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.shardcountry.C_CountryDayStart;
import com.saaenmadsen.shardworld.actors.shardcountry.CountryMainActor;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.statistics.CountryDayStats;
import com.saaenmadsen.shardworld.statistics.WorldDayStats;
import com.saaenmadsen.shardworld.statistics.WorldStatisticsReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShardWorldActor extends AbstractBehavior<ShardWorldActor.WorldCommand> {
    private int worldDay;
    private WorldSettings worldSettings;

    private WorldStatisticsReceiver worldStatisticsReceiver;

    private List<ActorRef<CountryMainActor.CountryMainActorCommand>> allCountries = new ArrayList<>();


    public interface WorldCommand {
    }

    public static Behavior<WorldCommand> create(WorldSettings worldSettings, WorldStatisticsReceiver worldStatisticsReceiver) {
        return Behaviors.setup(
                context -> {
                    ShardWorldActor worldActor = new ShardWorldActor(context, worldSettings, worldStatisticsReceiver);
                    return worldActor;
                });

    }

    public ShardWorldActor(ActorContext<WorldCommand> context, WorldSettings worldSettings, WorldStatisticsReceiver worldStatisticsReceiver) {
        super(context);
        this.worldStatisticsReceiver = worldStatisticsReceiver;
        getContext().getLog().info("ShardWorld Constructor Start");
        this.worldSettings = worldSettings;
        this.worldDay = 1;

        for (int i = 0; i < worldSettings.countryCount(); ++i) {
            String companyName = "country-" + i;
            allCountries.add(context.spawn(CountryMainActor.create(worldSettings, getContext().getSelf()), companyName));
        }
    }

    @Override
    public Receive<ShardWorldActor.WorldCommand> createReceive() {
        getContext().getLog().info("ShardCountry createReceive");
        return newReceiveBuilder()
                .onMessage(C_ShardWorldSystemStart.class, this::onShardWorldSystemStart)
                .onMessage(C_WorldDayEnd.class, this::onWorldDayEnd)
                .build();
    }

    private Behavior<WorldCommand> onShardWorldSystemStart(C_ShardWorldSystemStart message) {
        getContext().getLog().info("onNewDayStartReceived message received: {}", message);
        for (ActorRef<CountryMainActor.CountryMainActorCommand> country : allCountries) {
            country.tell(new C_CountryDayStart(worldDay));
        }
        return Behaviors.same();
    }

    private Behavior<WorldCommand> onWorldDayEnd(C_WorldDayEnd message) {
        getContext().getLog().info("onEndMarketDayCycle order received: {}", message);
        worldStatisticsReceiver.addDay(new WorldDayStats(message.dayId(), new CountryDayStats[]{message.countryDayStats()}));
        if (message.dayId() == this.worldDay) {
            if (this.worldDay < worldSettings.maxDaysToRun()) {
                this.worldDay++;
                for (ActorRef<CountryMainActor.CountryMainActorCommand> country : allCountries) {
                    country.tell(new C_CountryDayStart(message.dayId() + 1));
                }
                return Behaviors.same();
            } else {
                synchronized (worldStatisticsReceiver) {
                    worldStatisticsReceiver.notifyAll();
                }
                return Behaviors.stopped();
            }
        } else {
            throw new IllegalArgumentException("Attempt to end day " + message.dayId() + " while world is at day " + this.worldDay);
        }
    }

}
