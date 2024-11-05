package com.saaenmadsen.shardworld.actors.shardcountry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.statistics.MarketDayStats;

public record C_EndMarketDayCycle (
        int dayId,
        MarketDayStats marketDayStats
) implements A_ShardCountry.CountryMainActorCommand {

    public static C_EndMarketDayCycle fromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_EndMarketDayCycle.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson(){

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}