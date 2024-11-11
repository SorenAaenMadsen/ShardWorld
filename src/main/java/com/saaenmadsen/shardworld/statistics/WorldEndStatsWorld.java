package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaenmadsen.shardworld.ShardWorld;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.List;
import java.util.stream.Collectors;

public record WorldEndStatsWorld(@JsonIgnore StockListing finalWorldTotalStock,
                                 List<WorldEndStatsCountry> worldEndStatsCountries,
                                 PrintableStockListing finalWorldTotalStockMap) {

    public WorldEndStatsWorld(StockListing finalWorldTotalStock, List<WorldEndStatsCountry> worldEndStatsCountries) {
        this(finalWorldTotalStock, worldEndStatsCountries, new PrintableStockListing(finalWorldTotalStock));
    }


}

