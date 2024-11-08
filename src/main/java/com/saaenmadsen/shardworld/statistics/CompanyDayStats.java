package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaenmadsen.shardworld.actors.company.DailyReport;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.List;

public record CompanyDayStats(@JsonIgnore DailyReport dailyReport,
                              String companyId,
                              java.util.List<KnownRecipe> companyRecipes,
                              @JsonIgnore StockListing unsoldGoods,
                              @JsonIgnore StockListing companyWarehouse,
                              Long dayEndLiquidity,
                              PrintableStockListing companyWarehouseMap,
                              PrintableStockListing unsoldGoodsMap,
                              PrintableDailyReport dailyReportPrint) {

    public CompanyDayStats(DailyReport dailyReport, List<KnownRecipe> companyRecipes, StockListing unsoldGoods, StockListing companyWarehouse) {
        this(
                dailyReport,
                dailyReport.getCompanyId(),
                companyRecipes,
                unsoldGoods,
                companyWarehouse,
                dailyReport.getDayEndLiquidity(),
                new PrintableStockListing(companyWarehouse),
                new PrintableStockListing(unsoldGoods),
                new PrintableDailyReport(dailyReport)
        );
    }

}
