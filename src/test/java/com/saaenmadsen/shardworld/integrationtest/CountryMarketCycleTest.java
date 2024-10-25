package com.saaenmadsen.shardworld.integrationtest;

import com.saaenmadsen.shardworld.Main;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.integrationtest.util.IntegrationTestUtil;
import com.saaenmadsen.shardworld.statistics.WorldEndStatsWorld;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class CountryMarketCycleTest {

    private static final Logger log = LoggerFactory.getLogger(Main.class);


    @Test
    public void runWorldWithTestUtil() throws InterruptedException {
        WorldSettings worldSettings = new WorldSettings(10, 1, 10);

        IntegrationTestUtil countryLevelIntegrationTestUtil = new IntegrationTestUtil(worldSettings);

        WorldEndStatsWorld report = countryLevelIntegrationTestUtil.runWorld();

        assertThat( report.finalWorldTotalStock().getStockAmount(StockKeepUnit.FIREWOOD_KG), greaterThan(0));
        assertThat( report.finalWorldTotalStock().getStockAmount(StockKeepUnit.WOOD_KG), greaterThan(0));
    }
}
