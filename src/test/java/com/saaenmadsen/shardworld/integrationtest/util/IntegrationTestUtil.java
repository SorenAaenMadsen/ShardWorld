package com.saaenmadsen.shardworld.integrationtest.util;


import akka.actor.typed.ActorSystem;
import com.saaenmadsen.shardworld.actors.shardworld.C_ShardWorldSystemStart;
import com.saaenmadsen.shardworld.actors.shardworld.ShardWorldActor;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.statistics.WorldStatisticsReceiver;

public class IntegrationTestUtil {
    private ActorSystem<ShardWorldActor.WorldCommand> worldActor;
    private WorldStatisticsReceiver worldStatisticsReceiver;

    public IntegrationTestUtil(WorldSettings worldSettings) {
        worldStatisticsReceiver = new WorldStatisticsReceiver(worldSettings);

        worldActor = ActorSystem.create(ShardWorldActor.create(worldSettings, worldStatisticsReceiver), "MyWorld");

    }

    public WorldStatisticsReceiver runWorld(){
        worldActor.tell(new C_ShardWorldSystemStart());

        synchronized (worldStatisticsReceiver) {
            try {
                worldStatisticsReceiver.wait(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return worldStatisticsReceiver;
    }
}
