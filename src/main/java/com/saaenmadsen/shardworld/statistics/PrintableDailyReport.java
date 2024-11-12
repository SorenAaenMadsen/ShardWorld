package com.saaenmadsen.shardworld.statistics;

import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;

public class PrintableDailyReport {
    String dailyReport;
    private PrintableStockListing unsoldGoods;
    private PrintableStockListing forSaleList;

    public PrintableDailyReport(CompanyDailyReport companyDailyReport) {
        this.dailyReport = companyDailyReport.getDailyReport();
        this.unsoldGoods = new PrintableStockListing(companyDailyReport.getUnsoldGoods());
        this.forSaleList = new PrintableStockListing(companyDailyReport.getForSaleList());
    }
}
