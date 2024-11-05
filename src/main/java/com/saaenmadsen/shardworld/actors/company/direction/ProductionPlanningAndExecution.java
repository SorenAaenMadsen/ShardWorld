package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.DailyReport;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;

public class ProductionPlanningAndExecution {

    public ProductionPlanningAndExecution(CompanyInformation companyInformation, DailyReport dailyReport) {
        doProduction(companyInformation, dailyReport);
    }


    private void doProduction(CompanyInformation companyInformation, DailyReport dailyReport) {
        RecipeChoiceReport toWorkRecipe = RecipeChoiceReport.findRecipeWithHighestProjectedProfit(companyInformation, companyInformation.getPriceList());
        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : toWorkRecipe.productionChoices()) {
            String message = companyInformation.getCompanyId() + " production " + productionChoice.recipe().name() + " a total of " + productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials() + " times.";
            productionChoice.recipe().runProduction(productionChoice.productionImpactReport().maxProductionBeforeRunningOutOfTimeOrMaterials(), companyInformation.getWarehouse());
            dailyReport.appendToDailyReport(message);
        }
    }

}
