package com.saaenmadsen.shardworld.actors.shardworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public record C_ShardWorldAdvanceDays(int numberOfDays
) implements A_ShardWorld.WorldCommand {

    public static C_ShardWorldAdvanceDays fromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_ShardWorldAdvanceDays.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}