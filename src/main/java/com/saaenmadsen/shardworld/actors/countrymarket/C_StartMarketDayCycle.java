package com.saaenmadsen.shardworld.actors.countrymarket;

import akka.actor.typed.ActorRef;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.actors.company.A_ShardCompany;
import com.saaenmadsen.shardworld.actors.popgroup.A_PopGroup;

import java.util.List;

public record C_StartMarketDayCycle(
        int dayId,
        List<ActorRef<A_ShardCompany.ShardCompanyCommand>> allCompanies,
        List<ActorRef<A_PopGroup.PopGroupCommand>> allPopGroups
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

    @Override
    public String toString() {
        return toJson();
    }
}
