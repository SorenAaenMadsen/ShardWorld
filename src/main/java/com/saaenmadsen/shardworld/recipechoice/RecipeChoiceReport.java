package com.saaenmadsen.shardworld.recipechoice;

import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.ArrayList;
import java.util.List;

public record RecipeChoiceReport(List<RecipeChoiceReportElement> productionChoices,
                                 List<RecipeChoiceReportElement> nonSelectedChoices) {

    public record RecipeChoiceReportElement(Recipe recipe, ProductionImpactReport productionImpactReport,
                                            int projectedProfit, String selectionReport) {
        @Override
        public String toString() {
            return "RecipeChoiceReportElement{" +
                    "recipe=" + recipe.name() +
                    ", productionImpactReport=" + productionImpactReport +
                    ", projectedProfit=" + projectedProfit +
                    '}';
        }
    }

    public static RecipeChoiceReport findRecipeWithHighestProjectedProfit(List<KnownRecipe> availableRecipies, StockListing myRawMaterials, PriceList priceList, int workTimeAvailable) {
        int projectedProfit = 0;

        RecipeChoiceReportElement chosenRecipe = null;

        ArrayList<RecipeChoiceReportElement> selectedElements = new ArrayList<>();
        ArrayList<RecipeChoiceReportElement> nonSelectedElements = new ArrayList<>();

        for (KnownRecipe availableKnownRecipe : availableRecipies) {

            int thisProjectedProfit = availableKnownRecipe.recipe().calculateProfitPrWorkTenMin(priceList);
            ProductionImpactReport rawMaterialForProductionReport = availableKnownRecipe.recipe().evaluateRawMaterialImpact(workTimeAvailable, myRawMaterials);
            boolean usingRelevantAmountOfWorkTime = (rawMaterialForProductionReport.leftOverWorkTime() < (workTimeAvailable / 2));

            if (usingRelevantAmountOfWorkTime) {
                if (thisProjectedProfit > projectedProfit) {
                    projectedProfit = thisProjectedProfit;
                    selectedElements.add(new RecipeChoiceReportElement(availableKnownRecipe.recipe(), rawMaterialForProductionReport, projectedProfit, "Projected profit: " + thisProjectedProfit));
                } else {
                    nonSelectedElements.add(new RecipeChoiceReportElement(availableKnownRecipe.recipe(), rawMaterialForProductionReport, projectedProfit, "Other recipies have higher projected profit than " + thisProjectedProfit));
                }
            } else {
                nonSelectedElements.add(new RecipeChoiceReportElement(availableKnownRecipe.recipe(), rawMaterialForProductionReport, projectedProfit, "Not able to use a resonable amount of worktime. " + rawMaterialForProductionReport.leftOverWorkTime() + " / " + (workTimeAvailable / 2)));
            }
        }

        if (chosenRecipe == null) {

            return new RecipeChoiceReport(selectedElements, nonSelectedElements);
        } else {

            return new RecipeChoiceReport(selectedElements, nonSelectedElements);
        }
    }

}
