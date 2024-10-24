package com.saaenmadsen.shardworld.integrationtest;

import akka.Done;
import akka.actor.typed.ActorSystem;
import com.saaenmadsen.shardworld.Main;
import com.saaenmadsen.shardworld.actors.shardworld.C_ShardWorldSystemStart;
import com.saaenmadsen.shardworld.actors.shardworld.ShardWorldActor;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.statistics.WorldStatisticsReceiver;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Future;

public class CountryMarketCycleTest {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    @Test
    public void testWorldRuns() throws InterruptedException {
        WorldSettings worldSettings = new WorldSettings(10, 1, 10);
        WorldStatisticsReceiver worldStatisticsReceiver = new WorldStatisticsReceiver(worldSettings);

        ActorSystem<ShardWorldActor.WorldCommand> worldActor = ActorSystem.create(ShardWorldActor.create(worldSettings, worldStatisticsReceiver), "MyWorld");
        worldActor.tell(new C_ShardWorldSystemStart());

        synchronized (worldActor){
            worldActor.wait(4000);
        }

        log.info(worldStatisticsReceiver.toString());



    }
}
