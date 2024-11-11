package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.Main;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettings;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class WorldStatisticsReceiver {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private final WorldSettings worldSettings;
    private final int maxDayReportsToKeep;
    private final String recipies;
    private final String stockKeepUnits;
    private WorldEndStatsWorld worldEndStatus;


    List<WorldDayStats> dayStatistics = new ArrayList<>();

    public WorldStatisticsReceiver(WorldSettings worldSettings, int maxDayReportsToKeep) {
        this.worldSettings = worldSettings;
        this.maxDayReportsToKeep = maxDayReportsToKeep;
        this.recipies = Arrays.stream(Recipe.values()).map(Recipe::toString).collect(Collectors.joining(","));
        this.stockKeepUnits = Arrays.stream(Recipe.values()).map(Recipe::toString).collect(Collectors.joining(","));
    }

    public void addDay(WorldDayStats worldDayStats) {
        dayStatistics.add(worldDayStats);
        if(dayStatistics.size()>maxDayReportsToKeep){
            dayStatistics.removeFirst();
        }
    }

    public WorldEndStatsWorld summarize(){

        List<WorldEndStatsCountry> worldEndStatsCountries = new ArrayList<>();
        StockListing finalTotalWorldStock = StockListing.createEmptyStockListing();
        for (CountryDayStats countryDayStats : dayStatistics.getLast().countryDayStats()) {
            StockListing finalTotalCountryStock = StockListing.createEmptyStockListing();
            for (CompanyDayStats companyDayStats : countryDayStats.companyDayStats()) {
                finalTotalCountryStock.addStockFromList(companyDayStats.companyWarehouse());
            }
            worldEndStatsCountries.add(new WorldEndStatsCountry(finalTotalCountryStock));
            finalTotalWorldStock.addStockFromList(finalTotalCountryStock);
        }
        this.worldEndStatus = new WorldEndStatsWorld(finalTotalWorldStock, worldEndStatsCountries);
        writeToFile();
        return worldEndStatus;
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

    public void writeToFile() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS_Z");
            String fileName = "worldrunreports/ShardWorldRun" + simpleDateFormat.format(new Date())+ ".json";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(this.toString());
            log.info("Written report to " + fileName);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
