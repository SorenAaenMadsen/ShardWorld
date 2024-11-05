package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaenmadsen.shardworld.modeltypes.PriceList;

public record MarketDayStats(
        @JsonIgnore PriceList pricelistDayStart,
        @JsonIgnore PriceList pricelistDayEnd,
        PrintablePriceList pricelistDayStartMap,
        PrintablePriceList pricelistDayEndap
) {
    public MarketDayStats(PriceList pricelistDayStart, PriceList pricelistDayEnd) {
        this(pricelistDayStart, pricelistDayEnd, new PrintablePriceList(pricelistDayStart), new PrintablePriceList(pricelistDayEnd));
    }
}
