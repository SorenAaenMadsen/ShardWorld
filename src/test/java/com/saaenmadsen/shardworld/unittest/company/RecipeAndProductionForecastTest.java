package com.saaenmadsen.shardworld.unittest.company;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.DailyReport;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.actors.company.ShardCompany;
import com.saaenmadsen.shardworld.actors.company.direction.DailyDirectionMeeting;
import com.saaenmadsen.shardworld.recipechoice.ProductionImpactReport;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeAndProductionForecastTest {

    @Test
    public void RecipeProjectedProfitTest() {
        Recipe recipe = Recipe.GATHER_FIREWOOD;
        PriceList priceList = new PriceList();
        int projectedProfit = recipe.calculateProfitPrWorkTenMin(priceList);
        assertEquals(26, projectedProfit, "10 firewood at price 8 pr kg. divided by 3 work units");
    }

    @Test
    public void ProductionImpactReportTest() {
        Recipe recipe = Recipe.PRIMITIVE_WOODEN_SHUE;
        StockListing stockListing = StockListing.createEmptyStockListing();
        stockListing.setSkuCount(StockKeepUnit.WOOD_KG.getArrayId(), 100);
        ProductionImpactReport report = recipe.evaluateRawMaterialImpact(200, stockListing);
        assertEquals(8, report.maxProductionBeforeRunningOutOfTimeOrMaterials());
        assertEquals(8, report.leftOverWorkTime());
        assertEquals(84, report.leftOverStock().getSkuCount(StockKeepUnit.WOOD_KG.getArrayId()));
        assertEquals(16, report.usedRawMaterial().getSkuCount(StockKeepUnit.WOOD_KG.getArrayId()));

    }

    @Test
    public void PrepareBuyListForCompanyTest(){
        ArrayList<KnownRecipe> knownRecipes = new ArrayList<>();
        knownRecipes.add(new KnownRecipe(Recipe.PRIMITIVE_WOODEN_SHUE, 0));
        StockListing buylist = ShardCompany.buildBuyList(knownRecipes, 100);
        assertEquals(8, buylist.getSkuCount(StockKeepUnit.WOOD_KG.getArrayId()));

    }

    @Test
    public void DailyDirectionMeeting_CompanySoldAllTenWoodenShues_Test(){
        CompanyInformation companyInformation = new CompanyInformation("testcompany");
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.PRIMITIVE_WOODEN_SHUE, 0));
        DailyReport dailyReport = new DailyReport();

        StockListing forSaleList = StockListing.createEmptyStockListing();
        forSaleList.addStockAmount(StockKeepUnit.PAIR_OF_SHUES_WOODEN.getArrayId(), 10);
        dailyReport.setForSaleList(forSaleList);

        StockListing unsoldList = StockListing.createEmptyStockListing();
        unsoldList.addStockAmount(StockKeepUnit.PAIR_OF_SHUES_WOODEN.getArrayId(), 0);
        dailyReport.setUnsoldGoods(unsoldList);


        DailyDirectionMeeting meeting = new DailyDirectionMeeting(companyInformation,dailyReport);

        KnownRecipe updatedKnownRecipe = companyInformation.getKnownRecipes().getFirst();
        assertThat("Expecting the daily sale value to change",
                updatedKnownRecipe.getExpectedDailySaleValue_daily5percentChange(),
                greaterThan(120));
        assertThat("Expecting the daily sale value to change",
                updatedKnownRecipe.getExpectedDailySaleValue_daily10percentChange(),
                equalTo(250));
        assertThat("Expecting the daily sale value to change",
                updatedKnownRecipe.getExpectedDailySaleValue_daily20percentChange(),
                equalTo(500));

    }
}
