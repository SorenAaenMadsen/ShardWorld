package com.saaenmadsen.shardworld.statistics;

import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.List;

public record WorldEndStatsWorld(StockListing finalWorldTotalStock, List<WorldEndStatsCountry> worldEndStatsCountries) {
}
