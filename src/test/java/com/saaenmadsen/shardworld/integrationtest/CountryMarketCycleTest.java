package com.saaenmadsen.shardworld.integrationtest;

import akka.actor.typed.ActorSystem;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.actors.shardcountry.C_CountryDayStart;
import com.saaenmadsen.shardworld.actors.shardcountry.C_EndMarketDayCycle;
import com.saaenmadsen.shardworld.actors.shardcountry.CountryMainActor;
import com.saaenmadsen.shardworld.statistics.CountryStatisticsReceiver;
import org.junit.jupiter.api.Test;

public class CountryMarketCycleTest {

    @Test
    public void testSimpleMarketCycle() throws InterruptedException {


        CountryStatisticsReceiver statsReceiver = new CountryStatisticsReceiver();
        WorldSettings worldSettings = new WorldSettings(10);
        ActorSystem<CountryMainActor.CountryMainActorCommand> countryActor = ActorSystem.create(CountryMainActor.create(worldSettings, statsReceiver), "MyCountryActor");
        countryActor.tell(new C_CountryDayStart(1));
        countryActor.tell(new C_EndMarketDayCycle(1));


        Thread.sleep(4000);
    }
}
