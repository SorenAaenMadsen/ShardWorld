package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.DailyReport;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.constants.Recipe;

/**
 * At the daily direction meeting, decisions are taken regarding
 * - tomorrows production.
 */
public class DailyDirectionMeeting {
    public DailyDirectionMeeting(CompanyInformation companyInformation, DailyReport dailyReport) {
        for (KnownRecipe myRecipe : companyInformation.getKnownRecipes()) {
            int totalDailySaleValueForRecipeOutputs = 0;
            for (Recipe.SkuAndCount output : myRecipe.getRecipe().getOutputs()) {
                int putForSaleCount = dailyReport.getForSaleList().getStockAmount(output.sku());
                int notSoldCount = dailyReport.getUnsoldGoods().getStockAmount(output.sku());
                totalDailySaleValueForRecipeOutputs += (putForSaleCount - notSoldCount) * companyInformation.getPriceList().getPrice(output.sku().getArrayId());
            }
            myRecipe.updateExpectedDailySaleValue(totalDailySaleValueForRecipeOutputs);
        }
    }

}
