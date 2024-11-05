package com.saaenmadsen.shardworld.actors.company;

import com.saaenmadsen.shardworld.modeltypes.StockListing;

public class DailyReport {
    StringBuilder dailyReport;
    private StockListing unsoldGoods;
    private StockListing forSaleList;


    public DailyReport() {
        dailyReport = new StringBuilder();
    }

    public void appendToDailyReport(String section){
        dailyReport.append(" | "+section);
    }

    public void setUnsoldGoods(StockListing stockListing) {
        this.unsoldGoods = stockListing.createDuplicate();
    }

    public void setForSaleList(StockListing forSaleList) {
        this.forSaleList = forSaleList.createDuplicate();
    }

    public StockListing getUnsoldGoods() {
        return unsoldGoods;
    }

    public StockListing getForSaleList() {
        return forSaleList;
    }

    public String getDailyReport() {return this.dailyReport.toString();}
}
