package com.saaenmadsen.shardworld.actors.shardworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.statistics.CountryDayStats;

public record C_WorldDayEnd(
        int dayId,
        CountryDayStats countryDayStats) implements A_ShardWorld.WorldCommand {

    public static C_WorldDayEnd fromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_WorldDayEnd.class);
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