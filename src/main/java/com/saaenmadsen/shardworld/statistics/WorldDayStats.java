package com.saaenmadsen.shardworld.statistics;

public record WorldDayStats(
        int dayId,
        CountryDayStats[] countryDayStats
) {
}
