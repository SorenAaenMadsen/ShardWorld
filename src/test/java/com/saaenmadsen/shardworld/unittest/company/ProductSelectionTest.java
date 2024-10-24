package com.saaenmadsen.shardworld.unittest.company;

import com.saaenmadsen.shardworld.actors.company.recipechoice.RecipeChoice;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductSelectionTest {
//    @Test
//    public void RecipeChoiceTest() {
//        RecipeChoice.makeRecipeChoice()
//
//    }

    @Test
    public void RecipeProjectedProfitTest() {
        Recipe recipe = Recipe.GATHER_FIREWOOD;
        PriceList priceList = new PriceList();
        int projectedProfit = recipe.calculateProfitPrWorkTenMin(priceList);
        assertEquals(26, projectedProfit, "10 firewood at price 8 pr kg. divided by 3 work units");
    }
}
