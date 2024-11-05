package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.DailyReport;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.constants.Recipe;

/**
 */
public class DayEndEvaluationDirectionMeeting {

    public DayEndEvaluationDirectionMeeting(CompanyInformation companyInformation, DailyReport dailyReport) {
        updateExpectedDailySalesForRecipies(companyInformation, dailyReport);
        dailyReport.setLiquidityDayEnd(companyInformation.getMoneyBox().getMoney());
    }



    private static void updateExpectedDailySalesForRecipies(CompanyInformation companyInformation, DailyReport dailyReport) {
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
