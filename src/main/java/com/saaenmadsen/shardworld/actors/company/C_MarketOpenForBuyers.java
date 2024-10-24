package com.saaenmadsen.shardworld.actors.company;

import akka.actor.typed.ActorRef;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.actors.countrymarket.CountryMarket;
import com.saaenmadsen.shardworld.modeltypes.PriceList;

public record C_MarketOpenForBuyers(
        int dayId,
        PriceList priceList,

        ActorRef<CountryMarket.CountryMarketCommand> countryMarket

    ) implements ShardCompany.ShardCompanyCommand{

    public static C_MarketOpenForBuyers fromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_MarketOpenForBuyers.class);
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
