package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InnovateOurRecipiesBoardMeeting {

    public InnovateOurRecipiesBoardMeeting(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        if (companyInformation.getCulture().getInnovativenessLevel().getLevel() == 1) {
            return;
        } else {
            reduceNumberOfKnownRecipiesToMax5(companyInformation, companyDailyReport);
            investInBestOfXRandomProductionRecipes(companyInformation, companyDailyReport, companyInformation.getCulture().getInnovativenessLevel().getLevel() + 2);
        }
    }

    public static void investInNewCompletelyRandomProductionRecipe(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        Random dice = new Random();
        int newRecipeIndex = dice.nextInt(Recipe.values().length);
        Recipe newRecipe = Recipe.values()[newRecipeIndex];
        companyInformation.getKnownRecipes().add(new KnownRecipe(newRecipe, 0));
        companyDailyReport.appendToDailyReport("We have invested in " + newRecipe.name());
    }

    public static void reduceNumberOfKnownRecipiesToMax5(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        if (companyInformation.getKnownRecipes().size() > 5) {
            KnownRecipe worstRecipe = companyInformation.getKnownRecipes().stream().reduce(
                    (a, b) -> a.getExpectedDailySaleValue_daily10percentChange() < b.getExpectedDailySaleValue_daily10percentChange() ? a : b
            ).get();
            companyDailyReport.appendToDailyReport("Streamlined our production by retiring " + worstRecipe.recipe().name());
            companyInformation.getKnownRecipes().remove(worstRecipe);
        }
    }

    public static void investInBestOfXRandomProductionRecipes(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport, int numberOfIdeasToGenerate) {
        List<KnownRecipe> newIdeas = new ArrayList<>(companyInformation.getKnownRecipes());
        Random dice = new Random();
        for (int i = 0; i < numberOfIdeasToGenerate; ++i) {
            int newRecipeIndex = dice.nextInt(Recipe.values().length);
            newIdeas.add(new KnownRecipe(Recipe.values()[newRecipeIndex], 100));
        }

        RecipeChoiceReport report = RecipeChoiceReport.findRecipeWithHighestProjectedProfit(newIdeas, StockListing.createMaxedOutStockListing(), companyInformation.getPriceList(), 1000);

        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : report.productionChoices()) {
            companyInformation.getKnownRecipes().add(new KnownRecipe(productionChoice.recipe(), 0));
            companyDailyReport.appendToDailyReport("We have invested in " + productionChoice.recipe().name());
        }
    }
}
