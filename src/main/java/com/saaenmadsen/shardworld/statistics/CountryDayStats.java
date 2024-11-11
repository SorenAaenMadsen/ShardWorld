package com.saaenmadsen.shardworld.statistics;

import com.saaenmadsen.shardworld.modeltypes.StockListing;

public record CountryDayStats(
        int daySeqNo,
        String countryId,
        CompanyDayStats[] companyDayStats,
        MarketDayStats marketDayStats
) {
}
