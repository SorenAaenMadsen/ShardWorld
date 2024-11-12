package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaenmadsen.shardworld.actors.countrymarket.MarketDailyReport;

public record MarketDayStats(
        PrintablePriceList pricelistDayStartMap,
        PrintablePriceList pricelistDayEndap,
        PrintableStockListing itemsForSale,
        PrintableStockListing unsoldItems,
        long totalMarketTurnover,
        String managementReport,

        @JsonIgnore MarketDailyReport marketDailyReport
) {
    public MarketDayStats(MarketDailyReport marketDailyReport) {
        this(
                new PrintablePriceList(marketDailyReport.getPriceListDayStart()),
                new PrintablePriceList(marketDailyReport.getPriceListDayEnd()),
                new PrintableStockListing(marketDailyReport.getForSaleList()),
                new PrintableStockListing(marketDailyReport.getUnsoldGoods()),
                marketDailyReport.getMarketDayTotalTurnover(),
                marketDailyReport.getDailyReport(),
                marketDailyReport
        );
    }
}
