package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public record WorldEndStatsCountry(String countryId,
                                   @JsonIgnore StockListing finalCountryTotalStock,
                                   PrintableStockListing finalCountryTotalStockMap,
                                   MarketDayStats marketDayStats
) {

    public WorldEndStatsCountry(String countryId, StockListing finalCountryTotalStock, MarketDayStats marketDayStats) {
        this(countryId, finalCountryTotalStock, new PrintableStockListing(finalCountryTotalStock), marketDayStats);
    }

    public String getCountryId() {
        return countryId;
    }
}
