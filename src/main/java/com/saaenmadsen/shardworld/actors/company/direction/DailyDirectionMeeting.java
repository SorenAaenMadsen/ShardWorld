package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.DailyReport;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.constants.Recipe;

import java.util.Random;

/**
 * At the daily direction meeting, decisions are taken regarding
 * - tomorrows production.
 */
public final class DailyDirectionMeeting {

    public static void accept(CompanyInformation companyInformation, DailyReport dailyReport) {
        investInNewProductionRecipies(companyInformation, dailyReport);
    }


    private static void investInNewProductionRecipies(CompanyInformation companyInformation, DailyReport dailyReport) {


        String logMessage = companyInformation.getCompanyId() + " recipe adjustment: ";
        if(companyInformation.getMyRecipes().size()>5){
            logMessage += " removed " + companyInformation.getMyRecipes().getFirst().recipe().name();
            companyInformation.getMyRecipes().removeFirst();
        }
        Random dice = new Random();
        int newRecipeIndex = dice.nextInt(Recipe.values().length);
        Recipe newRecipe = Recipe.values()[newRecipeIndex];
        companyInformation.getMyRecipes().add(new KnownRecipe(newRecipe));
        logMessage += " added " + newRecipe.name();
        dailyReport.appendToDailyReport(logMessage);
//        getContext().getLog().info(logMessage);

    }
}
