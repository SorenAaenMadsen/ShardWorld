package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.List;

public record WorldEndStatsCountry(@JsonIgnore StockListing finalCountryTotalStock,
                                   PrintableStockListing finalCountryTotalStockMap) {
    public WorldEndStatsCountry(StockListing finalCountryTotalStock) {
        this(finalCountryTotalStock, new PrintableStockListing(finalCountryTotalStock));
    }
}
