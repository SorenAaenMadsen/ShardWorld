package com.saaenmadsen.shardworld.actors.popgroup;

import akka.actor.typed.ActorRef;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.actors.countrymarket.A_CountryMarket;
import com.saaenmadsen.shardworld.modeltypes.PriceList;

public record C_MarketOpenForPopBuyers(
        int dayId,
        PriceList priceList,
        ActorRef<A_CountryMarket.CountryMarketCommand> countryMarket
    ) implements A_PopGroup.PopGroupCommand{

    public static C_MarketOpenForPopBuyers fromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_MarketOpenForPopBuyers.class);
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
