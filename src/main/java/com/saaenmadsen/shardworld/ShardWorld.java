package com.saaenmadsen.shardworld;

import com.saaenmadsen.shardworld.actors.shardworld.A_ShardWorld;
import com.saaenmadsen.shardworld.actors.shardworld.C_ShardWorldAdvanceDays;
import com.saaenmadsen.shardworld.actors.shardworld.C_ShardWorldSystemStart;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldRunMode;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettings;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettingsBuilder;
import com.saaenmadsen.shardworld.statistics.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Comparator;
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
                .withMaxDaysToRun(1000)
                .withNumberOfCompaniesPrCountry(20)
                .withWorldRunMode(WorldRunMode.MANUAL_DAY_ADVANCE)
                .build();
        worldStatisticsReceiver = new WorldStatisticsReceiver(worldSettings, 20);
        worldActor = akka.actor.typed.ActorSystem.create(A_ShardWorld.create(worldSettings, worldStatisticsReceiver), "MyWorld");
    }

    public void start() {
        log.info("Starting ShardWorld");
        worldActor.tell(new C_ShardWorldSystemStart());
    }

    public void advanceOneDay() {
        log.info("Advancing ShardWorld another day");
        worldActor.tell(new C_ShardWorldAdvanceDays(1));
    }

    public WorldEndStatsWorld getLatestSummary() {
        return worldStatisticsReceiver.getLatestSummary();
    }

    public List<WorldStatusKeyValue> getWorldCompanyStatusHtmlTableInput() {
        List<WorldStatusKeyValue> worldStatus = new ArrayList<>();
        WorldDayStats lastReportedDay = worldStatisticsReceiver.getLastReportedDay();

        // Add all the countries and companies - and sort them.
        for (CountryDayStats countryDayStats : lastReportedDay.countryDayStats()) {
            String countryId = countryDayStats.countryId();
            for (CompanyDayStats companyDayStats : countryDayStats.companyDayStats()) {
                String label = countryId + " " + companyDayStats.companyId();
                String companyDailyOverviewHtml = buildCompanyDailyOverview(companyDayStats);
                worldStatus.add(new WorldStatusKeyValue(label, companyDailyOverviewHtml));
            }
        }
        worldStatus.sort(Comparator.comparing(WorldStatusKeyValue::label));

        worldStatus.add(0, new WorldStatusKeyValue("Day", "" + lastReportedDay.dayId()));


        return worldStatus;
    }

    @NotNull
    private static String buildCompanyDailyOverview(CompanyDayStats companyDayStats) {

        String companyDailyOverviewHtml =
                "<table style=\"font-weight:normal\">\n" +
                        "  <tr>\n" +
//                        "    <td  style=\"width:15px\">Company: <b>"+companyDayStats.companyId()+"</b></td>\n" +
//                        "    <th>Day: "+companyDayStats.day()+"</th>\n" +
                        "    <td style=\"width:250px\">Daily Revenue: <b>"+companyDayStats.marketDayRevenue()+"</b></td>\n" +
                        "    <td style=\"width:250px\" style=\"padding-left:15px\">Liquidity: <b>" + companyDayStats.companyDailyReport().getDayEndLiquidity()+ "</b></td>\n" +
                        "  </tr>\n" +
                        "</table>" +
                "<details>\n" +
                        "  <summary>Daily executive report</summary>\n" +
                        "  <p>" + companyDayStats.companyDailyReport().getDailyReport() + "</p>\n" +
                        "</details>";
        return companyDailyOverviewHtml;
    }

}