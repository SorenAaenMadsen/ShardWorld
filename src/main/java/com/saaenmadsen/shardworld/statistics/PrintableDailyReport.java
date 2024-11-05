package com.saaenmadsen.shardworld.statistics;

import com.saaenmadsen.shardworld.actors.company.DailyReport;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.HashMap;
import java.util.Map;

public class PrintableDailyReport {
    String dailyReport;
    private PrintableStockListing unsoldGoods;
    private PrintableStockListing forSaleList;

    public PrintableDailyReport(DailyReport dailyReport) {
        this.dailyReport = dailyReport.getDailyReport();
        this.unsoldGoods = new PrintableStockListing(dailyReport.getUnsoldGoods());
        this.forSaleList = new PrintableStockListing(dailyReport.getForSaleList());
    }
}
