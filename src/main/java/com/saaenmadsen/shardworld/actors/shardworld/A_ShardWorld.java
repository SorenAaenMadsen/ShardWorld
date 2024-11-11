package com.saaenmadsen.shardworld.actors.shardworld;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.shardcountry.A_ShardCountry;
import com.saaenmadsen.shardworld.actors.shardcountry.C_CountryDayStart;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldRunMode;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettings;
import com.saaenmadsen.shardworld.statistics.CountryDayStats;
import com.saaenmadsen.shardworld.statistics.WorldDayStats;
import com.saaenmadsen.shardworld.statistics.WorldStatisticsReceiver;

import java.util.ArrayList;
import java.util.List;

public class A_ShardWorld extends AbstractBehavior<A_ShardWorld.WorldCommand> {
    private int worldDay;
    private int manuallyAdvancedMaxDay = -1;
    private WorldSettings worldSettings;
    private int lastWorldDayEndReceived = 0;

    private WorldStatisticsReceiver worldStatisticsReceiver;

    private List<ActorRef<A_ShardCountry.CountryMainActorCommand>> allCountries = new ArrayList<>();


    public interface WorldCommand {
    }

    public static Behavior<WorldCommand> create(WorldSettings worldSettings, WorldStatisticsReceiver worldStatisticsReceiver) {
        return Behaviors.setup(
                context -> {
                    A_ShardWorld worldActor = new A_ShardWorld(context, worldSettings, worldStatisticsReceiver);
                    return worldActor;
                });

    }

    public A_ShardWorld(ActorContext<WorldCommand> context, WorldSettings worldSettings, WorldStatisticsReceiver worldStatisticsReceiver) {
        super(context);
        this.worldStatisticsReceiver = worldStatisticsReceiver;
        this.worldSettings = worldSettings;
        this.worldDay = 1;

        for (int i = 0; i < worldSettings.countryCount(); ++i) {
            String companyName = "country-" + i;
            allCountries.add(context.spawn(A_ShardCountry.create(worldSettings, getContext().getSelf()), companyName));
        }
    }

    @Override
    public Receive<A_ShardWorld.WorldCommand> createReceive() {
        getContext().getLog().info("World createReceive");
        return newReceiveBuilder()
                .onMessage(C_ShardWorldSystemStart.class, this::onShardWorldSystemStart)
                .onMessage(C_WorldDayEnd.class, this::onWorldDayEnd)
                .onMessage(C_ShardWorldAdvanceDays.class, this::onAdvanceDays)
                .build();
    }

    private Behavior<WorldCommand> onShardWorldSystemStart(C_ShardWorldSystemStart message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("World onNewDayStartReceived message received: {}", message);
        }
        for (ActorRef<A_ShardCountry.CountryMainActorCommand> country : allCountries) {
            country.tell(new C_CountryDayStart(worldDay));
        }
        return Behaviors.same();
    }

    private Behavior<WorldCommand> onWorldDayEnd(C_WorldDayEnd message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("World onEndMarketDayCycle order received: {}", message);
        }
        worldStatisticsReceiver.addDay(new WorldDayStats(message.dayId(), new CountryDayStats[]{message.countryDayStats()}));
        if (message.dayId() == this.worldDay) { // sanity check :)
            this.lastWorldDayEndReceived = message.dayId();
            return ifReadyAdvanceWorldToNextDay();
        } else {
            throw new IllegalArgumentException("Attempt to end day " + message.dayId() + " while world is at day " + this.worldDay);
        }
    }


    /**
     * World may run in auto-mode
     */
    private Behavior<WorldCommand> ifReadyAdvanceWorldToNextDay() {
        if (this.worldDay < worldSettings.maxDaysToRun()) { // This is not the last day of the world
            if (this.lastWorldDayEndReceived == this.worldDay) { // Only advance day if current day is calculated to end
                switch (worldSettings.worldRunMode()) {
                    case WorldRunMode.AUTO_RUN_TO_END -> {
                        advanceDay();
                        return Behaviors.same();
                    }
                    case WorldRunMode.MANUAL_DAY_ADVANCE -> {
                        if (manuallyAdvancedMaxDay > this.worldDay) {
                            advanceDay();
                        }
                        return Behaviors.same();
                    }
                }
            } else {
                getContext().getLog().info("ifReadyAdvanceWorldToNextDay did not advance the day, as the current day is not completed");
                return Behaviors.same();
            }

            throw new RuntimeException("World settings run mode is not set correctly.");

        } else {
            synchronized (worldStatisticsReceiver) {
                worldStatisticsReceiver.notifyAll();
            }
            return Behaviors.stopped();
        }
    }

    private void advanceDay() {
        this.worldDay++;
        for (ActorRef<A_ShardCountry.CountryMainActorCommand> country : allCountries) {
            int nextDay = this.worldDay;
            getContext().getLog().info("World next day start for day: {}", nextDay);
            country.tell(new C_CountryDayStart(nextDay));
        }
    }

    private Behavior<WorldCommand> onAdvanceDays(C_ShardWorldAdvanceDays message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("World onAdvanceDays order received: {}", message);
        }
        if (message.numberOfDays() != 1) {
            throw new IllegalArgumentException("Only 1 day advances are supported");
        }
        this.manuallyAdvancedMaxDay++;
        return ifReadyAdvanceWorldToNextDay();
    }

}
