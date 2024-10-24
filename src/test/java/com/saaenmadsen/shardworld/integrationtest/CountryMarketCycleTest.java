package com.saaenmadsen.shardworld.integrationtest;

import akka.actor.typed.ActorSystem;
import com.saaenmadsen.shardworld.actors.shardworld.C_ShardWorldSystemStart;
import com.saaenmadsen.shardworld.actors.shardworld.ShardWorldActor;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.actors.shardcountry.C_CountryDayStart;
import com.saaenmadsen.shardworld.actors.shardcountry.C_EndMarketDayCycle;
import com.saaenmadsen.shardworld.actors.shardcountry.CountryMainActor;
import com.saaenmadsen.shardworld.statistics.CountryStatisticsReceiver;
import org.junit.jupiter.api.Test;

public class CountryMarketCycleTest {

    /*
    @Test
    public void testSimpleMarketCycleFromCountry() throws InterruptedException {


        CountryStatisticsReceiver statsReceiver = new CountryStatisticsReceiver();
        WorldSettings worldSettings = new WorldSettings(10, 1);
        ActorSystem<CountryMainActor.CountryMainActorCommand> countryActor = ActorSystem.create(CountryMainActor.create(worldSettings, statsReceiver, getContext().getSelf()), "MyCountryActor");
        countryActor.tell(new C_CountryDayStart(1));
        countryActor.tell(new C_EndMarketDayCycle(1));


        Thread.sleep(4000);
    }*/

    @Test
    public void testWorldRuns() {
        WorldSettings worldSettings = new WorldSettings(10, 1, 10);
        ActorSystem<ShardWorldActor.WorldCommand> worldActor = ActorSystem.create(ShardWorldActor.create(worldSettings), "MyWorld");
        worldActor.tell(new C_ShardWorldSystemStart());

    }
}
