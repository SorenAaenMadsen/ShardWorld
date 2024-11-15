package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class ProductionPlanningAndExecution {

    public ProductionPlanningAndExecution(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        attemptToSetupProductionLinesForRecipies(companyInformation);

        List<KnownRecipe> availableProductionLines = companyInformation.getKnownRecipes().stream().filter(knownRecipe -> knownRecipe.isProductionLine()).collect(Collectors.toUnmodifiableList());

        RecipeChoiceReport productionLineSelectionReport = RecipeChoiceReport.evaluateRecipiesForProfitability(
                availableProductionLines,
                companyInformation.getWarehouse(),
                companyInformation.getPriceList(),
                companyInformation.calculateWorkTimeAvailable()
        );

        if (productionLineSelectionReport.productionChoices().isEmpty()) {
            String reasoning = productionLineSelectionReport.nonSelectedChoices().stream().map(nonSelected -> nonSelected.recipe().name() + ":" +nonSelected.selectionReport()).collect(Collectors.joining(","));
            companyDailyReport.appendToDailyReport("No production. " + reasoning);
        }
        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : productionLineSelectionReport.productionChoices()) {

            OptionalInt runs = productionChoice.recipe().maxProductionRunsWithRawMaterials(companyInformation.getWarehouse());

            if(runs.isPresent()) {
                productionChoice.recipe().runProduction(runs.getAsInt(), companyInformation.getWarehouse());
                companyDailyReport.appendToDailyReport("Executed " + productionChoice.recipe().name() + " a total of " + runs.getAsInt() + " times.");
            } else {
                companyDailyReport.appendToDailyReport("Executed " + productionChoice.recipe().name() + " a total of 0 times due to raw materials.");
            }
        }
    }

    private static void attemptToSetupProductionLinesForRecipies(CompanyInformation companyInformation) {
        companyInformation.getKnownRecipes().stream().filter(knownRecipe -> knownRecipe.isProductionLine() == false).forEach(knownRecipe -> {
            knownRecipe.setupProductionLine(companyInformation.getWarehouse());
        });
    }

}
