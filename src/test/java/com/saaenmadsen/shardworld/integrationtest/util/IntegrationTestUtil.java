package com.saaenmadsen.shardworld.integrationtest.util;


import akka.actor.typed.ActorSystem;
import com.saaenmadsen.shardworld.actors.shardworld.C_ShardWorldSystemStart;
import com.saaenmadsen.shardworld.actors.shardworld.A_ShardWorld;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.statistics.WorldEndStatsWorld;
import com.saaenmadsen.shardworld.statistics.WorldStatisticsReceiver;

public class IntegrationTestUtil {
    private ActorSystem<A_ShardWorld.WorldCommand> worldActor;
    private WorldStatisticsReceiver worldStatisticsReceiver;

    public IntegrationTestUtil(WorldSettings worldSettings) {
        worldStatisticsReceiver = new WorldStatisticsReceiver(worldSettings, 10);

        worldActor = ActorSystem.create(A_ShardWorld.create(worldSettings, worldStatisticsReceiver), "MyWorld");

    }

    public WorldEndStatsWorld runWorld(int timetoutMillis){
        worldActor.tell(new C_ShardWorldSystemStart());

        synchronized (worldStatisticsReceiver) {
            try {
                worldStatisticsReceiver.wait(timetoutMillis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return worldStatisticsReceiver.summarize();
    }
}
