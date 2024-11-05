package com.saaenmadsen.shardworld.actors.countrymarket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.actors.company.A_ShardCompany;

public record C_StartMarketDayCycle(
        int dayId,
        java.util.List<akka.actor.typed.ActorRef<A_ShardCompany.ShardCompanyCommand>> allCompanies
) implements A_CountryMarket.CountryMarketCommand {

    public static C_StartMarketDayCycle fromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_StartMarketDayCycle.class);
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
