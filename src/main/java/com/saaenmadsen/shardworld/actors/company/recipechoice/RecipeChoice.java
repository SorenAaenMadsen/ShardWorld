package com.saaenmadsen.shardworld.actors.company.recipechoice;

import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.SkuStock;

import java.util.List;

public class RecipeChoice {
    public static Recipe makeRecipeChoice(List<Recipe> availableRecipies, SkuStock myRawMaterials, PriceList priceList, int workTimeAvailable) {
        int projectedProfit = 0;
        Recipe chosenRecipe = null;
        for (Recipe availableRecipy : availableRecipies) {
            int thisProjectedProfit = availableRecipy.calculateProfitPrWorkTenMin(priceList);
            if(thisProjectedProfit > projectedProfit ){
                projectedProfit = thisProjectedProfit;
                chosenRecipe = availableRecipy;
            }
        }
        return chosenRecipe;
    }
}
