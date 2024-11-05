package com.saaenmadsen.shardworld.actors.company;

import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public class DailyReport {
    StringBuilder dailyReport;
    private StockListing unsoldGoods;
    private StockListing forSaleList;
    private Long dayEndLiquidity;


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

    public void setLiquidityDayEnd(long money) {
        this.dayEndLiquidity = money;
    }

    public Long getDayEndLiquidity() {
        return this.dayEndLiquidity;
    }
}
