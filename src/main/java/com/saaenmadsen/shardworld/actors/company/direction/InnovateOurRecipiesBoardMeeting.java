package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import com.saaenmadsen.shardworld.recipechoice.RecipeChoiceReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class InnovateOurRecipiesBoardMeeting {



    public InnovateOurRecipiesBoardMeeting(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport, StockListing unfulfilledOrdersAtMarket) {
        if (companyInformation.getCulture().getInnovativenessLevel().getLevel() == 1) {
            return;
        } else {
            reduceNumberOfKnownRecipiesToMax5(companyInformation, companyDailyReport);
//            investInBestOfXRandomProductionRecipes(companyInformation, companyDailyReport, unfulfilledOrdersAtMarket, companyInformation.getCulture().getInnovativenessLevel().getLevel() / 2);
            investInDesiredProductionRecipe(companyInformation, companyDailyReport, unfulfilledOrdersAtMarket, companyInformation.getCulture().getInnovativenessLevel().getLevel() / 2);
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

    public static void investInBestOfXRandomProductionRecipes(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport, StockListing unfulfilledOrdersAtMarket, int numberOfIdeasToGenerate) {
        List<KnownRecipe> newIdeas = new ArrayList<>(companyInformation.getKnownRecipes());
        Random dice = new Random();
        fillNewIdeasUpWithCompletelyRandomIdeas(numberOfIdeasToGenerate, dice, newIdeas);

        RecipeChoiceReport report = RecipeChoiceReport.findRecipeWithHighestProjectedProfit(newIdeas, StockListing.createMaxedOutStockListing(), companyInformation.getPriceList(), 1000);

        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : report.productionChoices()) {
            companyInformation.getKnownRecipes().add(new KnownRecipe(productionChoice.recipe(), 0));
            String ideas = newIdeas.stream().map(i -> i.toString()).collect(Collectors.joining(", "));
            companyDailyReport.appendToDailyReport("We have invested in " + productionChoice.recipe().name() + ". Our ideas were: " + ideas);
        }
    }

    private static void fillNewIdeasUpWithCompletelyRandomIdeas(int numberOfIdeasToGenerate, Random dice, List<KnownRecipe> newIdeas) {
        if(newIdeas.size()<numberOfIdeasToGenerate) {
            for (int i = 0; i < (numberOfIdeasToGenerate- newIdeas.size()); ++i) {
                int newRecipeIndex = dice.nextInt(Recipe.values().length);
                newIdeas.add(new KnownRecipe(Recipe.values()[newRecipeIndex], 100));
            }
        }
    }

    private static boolean recipeOutputIsDesired(List<Recipe.SkuAndCount> recipeOutputs, StockListing unfulfilledOrdersAtMarket) {
        for (Recipe.SkuAndCount recipeOutput : recipeOutputs) {
            if(unfulfilledOrdersAtMarket.getSkuCount(recipeOutput.sku().getArrayId())>0){
                return true;
            }
        }
        return false;
    }

    public static void investInDesiredProductionRecipe(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport, StockListing unfulfilledOrdersAtMarket, int numberOfIdeasToGenerate) {
        List<KnownRecipe> newIdeas = new ArrayList<>(companyInformation.getKnownRecipes());

        Random dice = new Random();

        List<Recipe> recipiesWithDesiredOutput = Arrays.stream(Recipe.values()).filter(recipe -> recipeOutputIsDesired(recipe.getOutputs(), unfulfilledOrdersAtMarket)).collect(Collectors.toUnmodifiableList());

        addIdeasProducingDesiredOutput(Math.min(recipiesWithDesiredOutput.size(), numberOfIdeasToGenerate), dice, recipiesWithDesiredOutput, newIdeas);


        fillNewIdeasUpWithCompletelyRandomIdeas(numberOfIdeasToGenerate-newIdeas.size(), dice, newIdeas);




        RecipeChoiceReport report = RecipeChoiceReport.findRecipeWithHighestProjectedProfit(newIdeas, StockListing.createMaxedOutStockListing(), companyInformation.getPriceList(), 1000);

        for (RecipeChoiceReport.RecipeChoiceReportElement productionChoice : report.productionChoices()) {
            companyInformation.getKnownRecipes().add(new KnownRecipe(productionChoice.recipe(), 0));
            String ideas = newIdeas.stream().map(i -> i.toString()).collect(Collectors.joining(", "));
            companyDailyReport.appendToDailyReport("We have invested in " + productionChoice.recipe().name() + ". Our ideas were: " + ideas);
        }
    }

    private static void addIdeasProducingDesiredOutput(int numberOfIdeasToGenerate, Random dice, List<Recipe> recipiesWithDesiredOutput, List<KnownRecipe> newIdeas) {
        if (recipiesWithDesiredOutput.size() > 0) {
            for (int i = 0; i < Math.min(numberOfIdeasToGenerate, recipiesWithDesiredOutput.size()); ++i) {
                int newRecipeIndex = dice.nextInt(recipiesWithDesiredOutput.size());
                KnownRecipe idea = new KnownRecipe(recipiesWithDesiredOutput.get(newRecipeIndex), 100);
                newIdeas.add(idea);
            }
        }
    }
}
