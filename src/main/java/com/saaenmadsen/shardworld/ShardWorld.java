package com.saaenmadsen.shardworld;

import com.saaenmadsen.shardworld.actors.shardworld.A_ShardWorld;
import com.saaenmadsen.shardworld.actors.shardworld.C_ShardWorldAdvanceDays;
import com.saaenmadsen.shardworld.actors.shardworld.C_ShardWorldSystemStart;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldRunMode;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettings;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettingsBuilder;
import com.saaenmadsen.shardworld.statistics.WorldEndStatsWorld;
import com.saaenmadsen.shardworld.statistics.WorldStatisticsReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
//import static com.sun.org.apache.xml.internal.serializer.Method.TEXT;

@SpringBootApplication
public class ShardWorld {
    private static final Logger log = LoggerFactory.getLogger(ShardWorld.class);

    public static ShardWorld instance = new ShardWorld();

    // Records for display in the browser.
    public record WorldStatusKeyValue(String label, String value) {
    }
    public record DataPoint(String label, int data) {
    }

    WorldSettings worldSettings;
    WorldStatisticsReceiver worldStatisticsReceiver;
    akka.actor.typed.ActorSystem<A_ShardWorld.WorldCommand> worldActor;

    public ShardWorld() {
        worldSettings = WorldSettingsBuilder
                .ofDefault()
                .withMaxDaysToRun(20)
                .withNumberOfCompaniesPrCountry(20)
                .withWorldRunMode(WorldRunMode.MANUAL_DAY_ADVANCE)
                .build();
        worldStatisticsReceiver = new WorldStatisticsReceiver(worldSettings, 20);
        worldActor = akka.actor.typed.ActorSystem.create(A_ShardWorld.create(worldSettings, worldStatisticsReceiver), "MyWorld");
    }

    public void start(){
        log.info("Starting ShardWorld");
        worldActor.tell(new C_ShardWorldSystemStart());
    }

    public void advanceOneDay(){
        log.info("Advancing ShardWorld another day");
        worldActor.tell(new C_ShardWorldAdvanceDays(1));
    }

    public WorldEndStatsWorld getLatestSummary() {
        return worldStatisticsReceiver.getLatestSummary();
    }

    public List<WorldStatusKeyValue> getWorldStatus() {
        List<WorldStatusKeyValue> worldStatus = new ArrayList<>();
        worldStatus.add(new WorldStatusKeyValue("test", "value"));
        worldStatus.add(new WorldStatusKeyValue("Day", ""+worldStatisticsReceiver.getLastReportedDay()));
        return worldStatus;
    }

}