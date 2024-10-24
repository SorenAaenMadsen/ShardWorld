package com.saaenmadsen.shardworld.actors.shardworld;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.shardcountry.C_CountryDayStart;
import com.saaenmadsen.shardworld.actors.shardcountry.C_EndMarketDayCycle;
import com.saaenmadsen.shardworld.actors.shardcountry.CountryMainActor;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.statistics.CountryStatisticsReceiver;

import java.util.ArrayList;
import java.util.List;

public class ShardWorldActor extends AbstractBehavior<ShardWorldActor.WorldCommand> {
    private int worldDay;
    private WorldSettings worldSettings;

    private List<ActorRef<CountryMainActor.CountryMainActorCommand>> allCountries = new ArrayList<>();


    public interface WorldCommand {
    }

    public static Behavior<WorldCommand> create(WorldSettings worldSettings) {
        return Behaviors.setup(
                context -> {
                    ShardWorldActor worldActor = new ShardWorldActor(context, worldSettings);
                    return worldActor;
                });

    }

    public ShardWorldActor(ActorContext<ShardWorldActor.WorldCommand> context, WorldSettings worldSettings) {
        super(context);
        getContext().getLog().info("ShardWorld Constructor Start");
        this.worldSettings = worldSettings;
        this.worldDay = 1;

        for (int i = 0; i < worldSettings.countryCount(); ++i) {
            String companyName = "country-" + i;
            allCountries.add(context.spawn(CountryMainActor.create(worldSettings, new CountryStatisticsReceiver(), getContext().getSelf()), companyName));
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

        if(message.dayId() == this.worldDay) {
            if(this.worldDay<worldSettings.maxDaysToRun()) {
                this.worldDay++;
                for (ActorRef<CountryMainActor.CountryMainActorCommand> country : allCountries) {
                    country.tell(new C_CountryDayStart(message.dayId() + 1));
                }
                return Behaviors.same();
            } else {
                return Behaviors.stopped();
            }
        } else {
            throw new IllegalArgumentException("Attempt to end day " + message.dayId() + " while world is at day " + this.worldDay);
        }


    }
}
