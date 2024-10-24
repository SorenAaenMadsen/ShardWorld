package com.saaenmadsen.shardworld.unittest.company;

import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.actors.company.ShardCompany;
import com.saaenmadsen.shardworld.recipechoice.ProductionImpactReport;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        knownRecipes.add(new KnownRecipe(Recipe.PRIMITIVE_WOODEN_SHUE));
        StockListing buylist = ShardCompany.buildBuyList(knownRecipes, 100);
        assertEquals(8, buylist.getSkuCount(StockKeepUnit.WOOD_KG.getArrayId()));

    }
}
