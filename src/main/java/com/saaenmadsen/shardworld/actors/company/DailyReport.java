package com.saaenmadsen.shardworld.actors.company;

import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public class DailyReport {
    private final String companyId;
    private final int dayId;
    StringBuilder dailyReport;
    private StockListing unsoldGoods;
    private StockListing forSaleList;
    private Long dayEndLiquidity;
    private Long marketDayRevenue;


    public DailyReport(String companyId, int dayId) {
        this.companyId = companyId;
        this.dayId = dayId;
        dailyReport = new StringBuilder();
        dailyReport.append("Company " + companyId + " on day "+dayId +":");
    }



    public void appendToDailyReport(String section){
        dailyReport.append("<br>"+section);
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

    public String getCompanyId() {
        return companyId;
    }

    public void setMarketDayRevenue(long marketDayRevenue) {
        this.marketDayRevenue = marketDayRevenue;
    }
}
