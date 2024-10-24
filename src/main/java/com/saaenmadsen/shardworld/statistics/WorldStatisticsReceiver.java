package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.actors.shardcountry.C_CountryDayStart;
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

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
