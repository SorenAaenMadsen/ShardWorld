package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;

import java.util.stream.Collectors;

public class ProductionPlanningAndExecution {

    public ProductionPlanningAndExecution(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        doProduction(companyInformation, companyDailyReport);
    }


    private void doProduction(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        RecipeChoiceReport toWorkRecipe = RecipeChoiceReport.findRecipeWithHighestProjectedProfit(companyInformation, companyInformation.getPriceList());
        if(toWorkRecipe.productionChoices().isEmpty()){
            String myRecipies = companyInformation.getKnownRecipes().stream().map(knownRecipe -> knownRecipe.getRecipe().name()).collect(Collectors.joining(", "));
            companyDailyReport.appendToDailyReport("No production, as none of the production lines ["+myRecipies+"] are profitable.");
        }
        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : toWorkRecipe.productionChoices()) {
            productionChoice.recipe().runProduction(productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials(), companyInformation.getWarehouse());
            companyDailyReport.appendToDailyReport("Executed " + productionChoice.recipe().name() + " a total of " + productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials() + " times.");
        }
    }

}
