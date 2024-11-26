package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyType;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class ProductionPlanningAndExecution {

    private final CompanyInformation companyInformation;
    private final CompanyDailyReport companyDailyReport;
    private final Optional<PartialProduction> partialProductionFromLast;

    public record PartialProduction(Recipe recipe, int alreadyAllocatedTime){
    }

    public ProductionPlanningAndExecution(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport, Optional<PartialProduction> partialProduction) {
        this.companyInformation = companyInformation;
        this.companyDailyReport = companyDailyReport;
        this.partialProductionFromLast = partialProduction;
    }

    public Optional<PartialProduction> execute(){
        if (companyInformation.getCompanyType().equals(CompanyType.PRODUCTION)) {


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
            return doProduction(productionLineSelectionReport);
        }
        return Optional.empty();
    }

    private Optional<PartialProduction> doProduction(RecipeChoiceReport productionLineSelectionReport) {
        int workTimeAvailable = companyInformation.calculateWorkTimeAvailable();

        if(partialProductionFromLast.isPresent()){
            PartialProduction productionToContinue = partialProductionFromLast.get();
            int workStillRemainingEvenAfterThisDay = productionToContinue.recipe.getWorkTimeTimes10Minutes() - productionToContinue.alreadyAllocatedTime - workTimeAvailable;
            if(workStillRemainingEvenAfterThisDay > 0){
                return Optional.of(new PartialProduction(productionToContinue.recipe(), productionToContinue.alreadyAllocatedTime + workTimeAvailable));
            } else {
                productionToContinue.recipe.addOutputsToStock(1, companyInformation.getWarehouse());
                workTimeAvailable = workTimeAvailable - (productionToContinue.recipe.getWorkTimeTimes10Minutes() - productionToContinue.alreadyAllocatedTime);
            }
        }


        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : productionLineSelectionReport.productionChoices()) {
            OptionalInt rawMaterialsLimit = productionChoice.recipe().findProductionLimitAccordingToRawMaterials(companyInformation.getWarehouse());
            int productionTimeLimit = workTimeAvailable / productionChoice.recipe().getWorkTimeTimes10Minutes();
            int maxRuns = rawMaterialsLimit.isPresent() ? Math.min(rawMaterialsLimit.getAsInt(), productionTimeLimit) : productionTimeLimit;
            workTimeAvailable = workTimeAvailable - (maxRuns * productionChoice.recipe().getWorkTimeTimes10Minutes());

            productionChoice.recipe().consumeRawMaterialsAndAddFinishedProducts(maxRuns, companyInformation.getWarehouse());
            if (maxRuns < 1) {
                companyDailyReport.appendToDailyReport("Partial production: " + productionChoice.recipe().name() + ". WorkTimeAvailable = " + workTimeAvailable + ". Production requires " + productionChoice.recipe().getWorkTimeTimes10Minutes());
                if(rawMaterialsLimit.isEmpty() || rawMaterialsLimit.getAsInt()>0) {
                    productionChoice.recipe().consumeRawMaterials(1, companyInformation.getWarehouse());
                    return Optional.of(new PartialProduction(productionChoice.recipe(), workTimeAvailable));
                }
            } else {
                companyDailyReport.appendToDailyReport("Executed " + productionChoice.recipe().name() + " a total of " + maxRuns + " times.");
            }

        }
        return Optional.empty();
    }

    private void attemptToSetupProductionLinesForRecipes(CompanyInformation companyInformation) {
        companyInformation.getKnownRecipes().stream().filter(knownRecipe -> knownRecipe.isProductionLine() == false).forEach(knownRecipe -> {
            knownRecipe.setupProductionLine(companyInformation.getWarehouse());
        });
    }

}
