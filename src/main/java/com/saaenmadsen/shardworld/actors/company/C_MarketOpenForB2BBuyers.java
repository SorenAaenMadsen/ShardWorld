package com.saaenmadsen.shardworld.actors.company;

import akka.actor.typed.ActorRef;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.actors.countrymarket.A_CountryMarket;
import com.saaenmadsen.shardworld.modeltypes.PriceList;

public record C_MarketOpenForB2BBuyers(
        int dayId,
        PriceList priceList,

        ActorRef<A_CountryMarket.CountryMarketCommand> countryMarket

    ) implements A_ShardCompany.ShardCompanyCommand{

    public static C_MarketOpenForB2BBuyers fromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_MarketOpenForB2BBuyers.class);
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

    @Override
    public String toString() {
        return toJson();
    }
}
