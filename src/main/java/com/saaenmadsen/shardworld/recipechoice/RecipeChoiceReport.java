package com.saaenmadsen.shardworld.recipechoice;

import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.ArrayList;
import java.util.List;

public record RecipeChoiceReport(List<RecipeChoiceReportElement> productionChoices) {

    public record RecipeChoiceReportElement(Recipe recipe, ProductionImpactReport productionImpactReport, int projectedProfit){}


    public static RecipeChoiceReport findRecipeWithHighestProjectedProfit(List<KnownRecipe> availableRecipies, StockListing myRawMaterials, PriceList priceList, int workTimeAvailable) {
        int projectedProfit = 0;

        RecipeChoiceReportElement chosenRecipe = null;

        for (KnownRecipe availableKnownRecipe : availableRecipies) {

            int thisProjectedProfit = availableKnownRecipe.recipe().calculateProfitPrWorkTenMin(priceList);
            ProductionImpactReport rawMaterialForProductionReport = availableKnownRecipe.recipe().evaluateRawMaterialImpact(workTimeAvailable, myRawMaterials);
            boolean usingRelevantAmountOfWorkTime = (rawMaterialForProductionReport.leftOverWorkTime() < (workTimeAvailable / 2));

            if(usingRelevantAmountOfWorkTime && thisProjectedProfit > projectedProfit ){
                projectedProfit = thisProjectedProfit;
                chosenRecipe = new RecipeChoiceReportElement(availableKnownRecipe.recipe(), rawMaterialForProductionReport, projectedProfit);
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
