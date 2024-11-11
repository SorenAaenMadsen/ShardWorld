package com.saaenmadsen.shardworld;

import com.saaenmadsen.shardworld.actors.shardworld.A_ShardWorld;
import com.saaenmadsen.shardworld.actors.shardworld.C_ShardWorldSystemStart;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.constants.WorldSettingsBuilder;
import com.saaenmadsen.shardworld.statistics.WorldEndStatsWorld;
import com.saaenmadsen.shardworld.statistics.WorldStatisticsReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import static com.sun.org.apache.xml.internal.serializer.Method.TEXT;

@SpringBootApplication
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static WorldEndStatsWorld summary;

    public static void main(String[] args) {

        log.info("Starting up Main");

        summary = runTheShardWorld();

        SpringApplication.run(Main.class, args);

        log.info("Done with Main");
    }

    private static WorldEndStatsWorld runTheShardWorld() {
        WorldSettings worldSettings = WorldSettingsBuilder
                .ofDefault()
                .withDaysToRun(100)
                .build();

        WorldStatisticsReceiver worldStatisticsReceiver = new WorldStatisticsReceiver(worldSettings, 10);
        akka.actor.typed.ActorSystem<A_ShardWorld.WorldCommand> worldActor = akka.actor.typed.ActorSystem.create(A_ShardWorld.create(worldSettings, worldStatisticsReceiver), "MyWorld");

        worldActor.tell(new C_ShardWorldSystemStart());

        synchronized (worldStatisticsReceiver) {
            try {
                worldStatisticsReceiver.wait(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return worldStatisticsReceiver.summarize();
    }


}