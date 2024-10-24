package com.saaenmadsen.shardworld.statistics;

public record MarketDayStats(
        int[] pricelistDayStart,
        int[] pricelistDayEnd
) {
}
