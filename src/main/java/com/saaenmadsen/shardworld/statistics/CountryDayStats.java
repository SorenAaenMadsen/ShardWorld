package com.saaenmadsen.shardworld.statistics;

public record CountryDayStats(
        int daySeqNo,
        CompanyDayStats[] companyDayStats,
        MarketDayStats[] marketDayStats
) {
}
