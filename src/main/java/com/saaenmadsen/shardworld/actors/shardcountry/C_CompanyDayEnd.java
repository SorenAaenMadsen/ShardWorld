package com.saaenmadsen.shardworld.actors.shardcountry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.statistics.CompanyDayStats;
import com.saaenmadsen.shardworld.statistics.CountryDayStats;

public record C_CompanyDayEnd(

        CompanyDayStats companyDayStats
) implements CountryMainActor.CountryMainActorCommand {

    public static C_CompanyDayEnd fromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_CompanyDayEnd.class);
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