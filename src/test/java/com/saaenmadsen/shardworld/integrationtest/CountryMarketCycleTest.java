package com.saaenmadsen.shardworld.integrationtest;

import com.saaenmadsen.shardworld.Main;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.integrationtest.util.IntegrationTestUtil;
import com.saaenmadsen.shardworld.statistics.WorldStatisticsReceiver;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryMarketCycleTest {

    private static final Logger log = LoggerFactory.getLogger(Main.class);


    @Test
    public void runWorldWithTestUtil() throws InterruptedException {
        WorldSettings worldSettings = new WorldSettings(10, 1, 10);

        IntegrationTestUtil countryLevelIntegrationTestUtil = new IntegrationTestUtil(worldSettings);

        WorldStatisticsReceiver report = countryLevelIntegrationTestUtil.runWorld();
//        log.info(report.toString());


    }
}
