package com.saaenmadsen.shardworld.actors.countrymarket;

import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public class MarketDailyReport {
    private final int dayId;
    StringBuilder dailyReport;
    private StockListing forSaleList;
    private StockListing unsoldGoods;
    private PriceList priceListDayStart;
    private PriceList priceListDayEnd;
    private Long marketDayTotalTurnover;

    public MarketDailyReport(int dayId) {
        this.dayId = dayId;
        dailyReport = new StringBuilder();
        dailyReport.append("Market report for day " + dayId + ":");
        marketDayTotalTurnover =0L;
    }


    public void appendToDailyReport(String section) {
        dailyReport.append("<br>" + section);
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

    public String getDailyReport() {
        return this.dailyReport.toString();
    }

    public void addToMarketDayTotalTurnover(long money) {
        this.marketDayTotalTurnover += money;
    }

    public Long getMarketDayTotalTurnover() {
        return this.marketDayTotalTurnover;
    }

    public PriceList getPriceListDayEnd() {
        return priceListDayEnd;
    }

    public void setPriceListDayEnd(PriceList priceListDayEnd) {
        this.priceListDayEnd = priceListDayEnd;
    }

    public PriceList getPriceListDayStart() {
        return priceListDayStart;
    }

    public void setPriceListDayStart(PriceList priceListDayStart) {
        this.priceListDayStart = priceListDayStart;
    }
}
