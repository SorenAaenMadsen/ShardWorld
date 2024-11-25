package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.List;

public record CompanyDayStats(@JsonIgnore CompanyDailyReport companyDailyReport,
                              String companyId,
                              int day,
                              java.util.List<KnownRecipe> companyRecipes,
                              @JsonIgnore StockListing unsoldGoods,
                              @JsonIgnore StockListing companyWarehouse,
                              Long dayEndLiquidity,
                              Long marketDayRevenue,
                              PrintableStockListing companyWarehouseMap,
                              PrintableStockListing unsoldGoodsMap,
                              PrintableDailyReport dailyReportPrint) {

    public CompanyDayStats(CompanyDailyReport companyDailyReport, List<KnownRecipe> companyRecipes, StockListing unsoldGoods, StockListing companyWarehouse) {
        this(
                companyDailyReport,
                companyDailyReport.getCompanyId(),
                companyDailyReport.getDayId(),
                companyRecipes,
                unsoldGoods,
                companyWarehouse,
                companyDailyReport.getDayEndLiquidity(),
                companyDailyReport.getMarketDayRevenue(),
                new PrintableStockListing(companyWarehouse),
                new PrintableStockListing(unsoldGoods),
                new PrintableDailyReport(companyDailyReport)
        );
    }

}
