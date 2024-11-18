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
        attemptToSetupProductionLinesForRecipes(companyInformation);

        List<KnownRecipe> availableProductionLines = companyInformation.getKnownRecipes().stream().filter(knownRecipe -> knownRecipe.isProductionLine()).collect(Collectors.toUnmodifiableList());

        RecipeChoiceReport productionLineSelectionReport = RecipeChoiceReport.evaluateRecipesForProfitability(
                availableProductionLines,
                companyInformation.getWarehouse(),
                companyInformation.getPriceList(),
                companyInformation.calculateWorkTimeAvailable()
        );

        if (productionLineSelectionReport.productionChoices().isEmpty()) {
            String reasoning = productionLineSelectionReport.nonSelectedChoices().stream().map(nonSelected -> nonSelected.recipe().name() + ":" + nonSelected.selectionReport()).collect(Collectors.joining(","));
            companyDailyReport.appendToDailyReport("No production. " + reasoning);
        }
        doProduction(companyInformation, companyDailyReport, productionLineSelectionReport);
    }

    private static void doProduction(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport, RecipeChoiceReport productionLineSelectionReport) {
        int workTimeAvailable = companyInformation.calculateWorkTimeAvailable();

        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : productionLineSelectionReport.productionChoices()) {
            OptionalInt rawMaterialsLimit = productionChoice.recipe().findProductionLimitAccordingToRawMaterials(companyInformation.getWarehouse());
            int productionTimeLimit = workTimeAvailable / productionChoice.recipe().getWorkTimeTimes10Minutes();
            int maxRuns = rawMaterialsLimit.isPresent() ? Math.min(rawMaterialsLimit.getAsInt(), productionTimeLimit) : productionTimeLimit;
            workTimeAvailable = workTimeAvailable - (maxRuns * productionChoice.recipe().getWorkTimeTimes10Minutes());

            productionChoice.recipe().runProduction(maxRuns, companyInformation.getWarehouse());
            companyDailyReport.appendToDailyReport("Executed " + productionChoice.recipe().name() + " a total of " + maxRuns + " times.");

        }
    }

    private static void attemptToSetupProductionLinesForRecipes(CompanyInformation companyInformation) {
        companyInformation.getKnownRecipes().stream().filter(knownRecipe -> knownRecipe.isProductionLine() == false).forEach(knownRecipe -> {
            knownRecipe.setupProductionLine(companyInformation.getWarehouse());
        });
    }

}
