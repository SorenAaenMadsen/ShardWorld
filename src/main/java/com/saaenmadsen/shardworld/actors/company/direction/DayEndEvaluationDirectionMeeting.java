package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.modeltypes.SkuAndCount;

/**
 */
public class DayEndEvaluationDirectionMeeting {

    public DayEndEvaluationDirectionMeeting(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        updateExpectedDailySalesForRecipes(companyInformation, companyDailyReport);
        companyDailyReport.setLiquidityDayEnd(companyInformation.getMoneyBox().getMoney());
    }



    private static void updateExpectedDailySalesForRecipes(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        for (KnownRecipe myRecipe : companyInformation.getKnownRecipes()) {
            int totalDailySaleValueForRecipeOutputs = 0;
            for (SkuAndCount output : myRecipe.getRecipe().getOutputs().skuAndCounts) {
                int putForSaleCount = companyDailyReport.getForSaleList().getStockAmount(output.sku());
                int notSoldCount = companyDailyReport.getUnsoldGoods().getStockAmount(output.sku());
                totalDailySaleValueForRecipeOutputs += (putForSaleCount - notSoldCount) * companyInformation.getPriceList().getPrice(output.sku().getArrayId());
            }
            myRecipe.updateExpectedDailySaleValue(totalDailySaleValueForRecipeOutputs);
        }
    }

}
