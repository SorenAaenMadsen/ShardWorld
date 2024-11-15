package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;

import java.util.List;
import java.util.stream.Collectors;

public class ProductionPlanningAndExecution {

    public ProductionPlanningAndExecution(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        attemptToSetupProductionLinesForRecipies(companyInformation);

        List<KnownRecipe> availableProductionLines = companyInformation.getKnownRecipes().stream().filter(knownRecipe -> knownRecipe.isProductionLine()).collect(Collectors.toUnmodifiableList());

        RecipeChoiceReport productionLineSelectionReport = RecipeChoiceReport.findRecipeWithHighestProjectedProfit(
                availableProductionLines,
                companyInformation.getWarehouse(),
                companyInformation.getPriceList(),
                companyInformation.calculateWorkTimeAvailable()
        );

        if (productionLineSelectionReport.productionChoices().isEmpty()) {
//            String reasoning = companyInformation.getKnownRecipes().stream().map(knownRecipe -> knownRecipe.getRecipe().name()).collect(Collectors.joining(", "));
            String reasoning = productionLineSelectionReport.nonSelectedChoices().stream().map(nonSelected -> nonSelected.recipe().name() + ":" +nonSelected.selectionReport()).collect(Collectors.joining(","));
            companyDailyReport.appendToDailyReport("No production. " + reasoning);
        }
        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : productionLineSelectionReport.productionChoices()) {
            productionChoice.recipe().runProduction(productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials(), companyInformation.getWarehouse());
            companyDailyReport.appendToDailyReport("Executed " + productionChoice.recipe().name() + " a total of " + productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials() + " times.");
        }
    }

    private static void attemptToSetupProductionLinesForRecipies(CompanyInformation companyInformation) {
        companyInformation.getKnownRecipes().stream().filter(knownRecipe -> knownRecipe.isProductionLine() == false).forEach(knownRecipe -> {
            knownRecipe.setupProductionLine(companyInformation.getWarehouse());
        });
    }

}
