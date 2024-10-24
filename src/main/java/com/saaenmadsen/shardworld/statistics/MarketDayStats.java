package com.saaenmadsen.shardworld.statistics;

import com.saaenmadsen.shardworld.modeltypes.PriceList;

public record MarketDayStats(
        PriceList pricelistDayStart,
        PriceList pricelistDayEnd
) {
}
