package com.saaenmadsen.shardworld.statistics;

import com.saaenmadsen.shardworld.actors.shardcountry.CountryMainActor;

import java.util.concurrent.ConcurrentSkipListSet;

public class CountryStatisticsReceiver implements CountryMainActor.CountryStatisticsReceiver {
    ConcurrentSkipListSet<CountryMainActor.CountryDayStatistics> dayStatistics = new ConcurrentSkipListSet<>();

    @Override
    public void addDay(CountryMainActor.CountryDayStatistics stats) {
        dayStatistics.add(stats);
    }

    public ConcurrentSkipListSet<CountryMainActor.CountryDayStatistics> getDayStatistics() {
        return dayStatistics;
    }
}
