package com.saaenmadsen.shardworld.recipechoice;

import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.ArrayList;
import java.util.List;

public record RecipeChoiceReport(List<RecipeChoiceReportElement> productionChoices) {

    public record RecipeChoiceReportElement(Recipe recipe, ProductionImpactReport productionImpactReport, int projectedProfit){}


    public static RecipeChoiceReport findRecipeWithHighestProjectedProfit(List<Recipe> availableRecipies, StockListing myRawMaterials, PriceList priceList, int workTimeAvailable) {
        int projectedProfit = 0;

        RecipeChoiceReportElement chosenRecipe = null;

        for (Recipe availableRecipy : availableRecipies) {

            int thisProjectedProfit = availableRecipy.calculateProfitPrWorkTenMin(priceList);
            ProductionImpactReport rawMaterialForProductionReport = availableRecipy.evaluateRawMaterialImpact(workTimeAvailable, myRawMaterials);
            boolean usingRelevantAmountOfWorkTime = (rawMaterialForProductionReport.leftOverWorkTime() < (workTimeAvailable / 2));

            if(usingRelevantAmountOfWorkTime && thisProjectedProfit > projectedProfit ){
                projectedProfit = thisProjectedProfit;
                chosenRecipe = new RecipeChoiceReportElement(availableRecipy, rawMaterialForProductionReport, projectedProfit);
            }

        }
        ArrayList<RecipeChoiceReportElement> reportElements = new ArrayList<>();
        if(chosenRecipe == null){
            return new RecipeChoiceReport(reportElements);
        } else {
            reportElements.add(chosenRecipe);
            return new RecipeChoiceReport(reportElements);
        }
    }
}
