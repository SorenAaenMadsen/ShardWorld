package com.saaenmadsen.shardworld.actors.company;

import akka.actor.typed.ActorRef;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.actors.countrymarket.CountryMarket;

public record C_NewPricelist(
        int dayId,
        ActorRef<CountryMarket.CountryMarketCommand> countryMarket
    ) implements ShardCompany.ShardCompanyCommand {

    public static C_NewPricelist fromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_NewPricelist.class);
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
