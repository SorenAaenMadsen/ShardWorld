package com.saaenmadsen.shardworld.statistics;

import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.WorldSettings;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class WorldStatisticsReceiver {
    private final WorldSettings worldSettings;
    private final String recipies;
    private final String stockKeepUnits;


    ConcurrentLinkedQueue<WorldDayStats> dayStatistics = new ConcurrentLinkedQueue<>();

    public WorldStatisticsReceiver(WorldSettings worldSettings) {
        this.worldSettings = worldSettings;
        this.recipies = Arrays.stream(Recipe.values()).map(Recipe::toString).collect(Collectors.joining(","));
        this.stockKeepUnits = Arrays.stream(Recipe.values()).map(Recipe::toString).collect(Collectors.joining(","));
    }

    public void addDay(WorldDayStats worldDayStats) {
        dayStatistics.add(worldDayStats);
    }

    @Override
    public String toString() {
        return "WorldStatisticsReceiver{" +
                "worldSettings=" + worldSettings +
                ", recipies='" + recipies + '\'' +
                ", stockKeepUnits='" + stockKeepUnits + '\'' +
                ", dayStatistics=" + dayStatistics +
                '}';
    }
}
