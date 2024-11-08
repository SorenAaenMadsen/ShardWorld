package com.saaenmadsen.shardworld.integrationtest;

import com.saaenmadsen.shardworld.Main;
import com.saaenmadsen.shardworld.actors.company.CompanyInformationBuilder;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyCultureBuilder;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyCulture_InnovativenessLevel;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.constants.WorldSettingsBuilder;
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
    public void runWorldWithTestUtil() {
        WorldSettings worldSettings = WorldSettingsBuilder.ofDefault().withAkkaMessageLogging().build();

        IntegrationTestUtil countryLevelIntegrationTestUtil = new IntegrationTestUtil(worldSettings);

        WorldEndStatsWorld report = countryLevelIntegrationTestUtil.runWorld(3000);

        assertThat("Either TIMBER_RAW_KG, WOOD_KG or FIREWOOD_KG should have been created in 10 days",
                report.finalWorldTotalStock().getStockAmount(StockKeepUnit.TIMBER_RAW_KG) +
                        report.finalWorldTotalStock().getStockAmount(StockKeepUnit.WOOD_KG) +
                        report.finalWorldTotalStock().getStockAmount(StockKeepUnit.FIREWOOD_KG),
                greaterThan(0));
    }

    @Test
    public void runWorldWith2CompaniesTrading() {
        WorldSettings worldSettings = WorldSettingsBuilder.ofDefault()
                .withStartCompany(CompanyInformationBuilder
                        .ofManual("FirewoodGatherer")
                        .withCulture(
                                CompanyCultureBuilder
                                        .ofRandom()
                                        .withInnovativeness(CompanyCulture_InnovativenessLevel.NO_INNOVATION)
                                        .build())
                        .withKnownRecipe(new KnownRecipe(Recipe.GATHER_FIREWOOD, 100))
                        .withMoney(10000)
                        .build())
                .withStartCompany(CompanyInformationBuilder
                        .ofManual("CharcoalBurner")
                        .withCulture(
                                CompanyCultureBuilder
                                        .ofRandom()
                                        .withInnovativeness(CompanyCulture_InnovativenessLevel.NO_INNOVATION)
                                        .build())
                        .withKnownRecipe(new KnownRecipe(Recipe.PRIMITIVE_CHARCOAL_BURNING, 100))
                        .withMoney(10000)
                        .build())
                .withOnlyTheManuallyAddedCompanies()
                .build();
        IntegrationTestUtil countryLevelIntegrationTestUtil = new IntegrationTestUtil(worldSettings);

        WorldEndStatsWorld report = countryLevelIntegrationTestUtil.runWorld(3000);

        assertThat("Firewood and Charcoal should have been created in 10 days",
                report.finalWorldTotalStock().getStockAmount(StockKeepUnit.FIREWOOD_KG),
                greaterThan(0));
        assertThat("Firewood and Charcoal should have been created in 10 days",
                report.finalWorldTotalStock().getStockAmount(StockKeepUnit.CHARCOAL),
                greaterThan(0));
    }


}
