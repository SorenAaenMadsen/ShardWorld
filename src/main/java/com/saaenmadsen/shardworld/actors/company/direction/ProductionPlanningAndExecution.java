package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.DailyReport;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;

import java.util.stream.Collectors;

public class ProductionPlanningAndExecution {

    public ProductionPlanningAndExecution(CompanyInformation companyInformation, DailyReport dailyReport) {
        doProduction(companyInformation, dailyReport);
    }


    private void doProduction(CompanyInformation companyInformation, DailyReport dailyReport) {
        RecipeChoiceReport toWorkRecipe = RecipeChoiceReport.findRecipeWithHighestProjectedProfit(companyInformation, companyInformation.getPriceList());
        if(toWorkRecipe.productionChoices().isEmpty()){
            String myRecipies = companyInformation.getKnownRecipes().stream().map(knownRecipe -> knownRecipe.getRecipe().name()).collect(Collectors.joining(", "));
            dailyReport.appendToDailyReport("No production, as none of the production lines ["+myRecipies+"] are profitable.");
        }
        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : toWorkRecipe.productionChoices()) {
            productionChoice.recipe().runProduction(productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials(), companyInformation.getWarehouse());
            dailyReport.appendToDailyReport("Executed " + productionChoice.recipe().name() + " a total of " + productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials() + " times.");
        }
    }

}
