package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.DailyReport;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InnovateOurRecipiesBoardMeeting {

    public InnovateOurRecipiesBoardMeeting(CompanyInformation companyInformation, DailyReport dailyReport) {
        if (companyInformation.getCulture().getInnovativenessLevel().getLevel() == 1) {
            return;
        } else {
            reduceNumberOfKnownRecipiesToMax5(companyInformation, dailyReport);
            investInBestOfXRandomProductionRecipes(companyInformation, dailyReport, companyInformation.getCulture().getInnovativenessLevel().getLevel() + 2);
        }
    }

    public static void investInNewCompletelyRandomProductionRecipe(CompanyInformation companyInformation, DailyReport dailyReport) {
        Random dice = new Random();
        int newRecipeIndex = dice.nextInt(Recipe.values().length);
        Recipe newRecipe = Recipe.values()[newRecipeIndex];
        companyInformation.getKnownRecipes().add(new KnownRecipe(newRecipe, 0));
        dailyReport.appendToDailyReport("We have invested in " + newRecipe.name());
    }

    public static void reduceNumberOfKnownRecipiesToMax5(CompanyInformation companyInformation, DailyReport dailyReport) {
        if (companyInformation.getKnownRecipes().size() > 5) {
            KnownRecipe worstRecipe = companyInformation.getKnownRecipes().stream().reduce(
                    (a, b) -> a.getExpectedDailySaleValue_daily10percentChange() < b.getExpectedDailySaleValue_daily10percentChange() ? a : b
            ).get();
            dailyReport.appendToDailyReport("Streamlined our production by retiring " + worstRecipe.recipe().name());
            companyInformation.getKnownRecipes().remove(worstRecipe);
        }
    }

    public static void investInBestOfXRandomProductionRecipes(CompanyInformation companyInformation, DailyReport dailyReport, int numberOfIdeasToGenerate) {
        List<KnownRecipe> newIdeas = new ArrayList<>(companyInformation.getKnownRecipes());
        Random dice = new Random();
        for (int i = 0; i < numberOfIdeasToGenerate; ++i) {
            int newRecipeIndex = dice.nextInt(Recipe.values().length);
            newIdeas.add(new KnownRecipe(Recipe.values()[newRecipeIndex], 100));
        }

        RecipeChoiceReport report = RecipeChoiceReport.findRecipeWithHighestProjectedProfit(newIdeas, StockListing.createMaxedOutStockListing(), companyInformation.getPriceList(), 1000);

        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : report.productionChoices()) {
            companyInformation.getKnownRecipes().add(new KnownRecipe(productionChoice.recipe(), 0));
            dailyReport.appendToDailyReport("We have invested in " + productionChoice.recipe().name());
        }
    }
}
