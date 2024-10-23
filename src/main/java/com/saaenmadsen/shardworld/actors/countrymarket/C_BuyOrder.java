package com.saaenmadsen.shardworld.actors.countrymarket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.modeltypes.SkuStock;

public record C_BuyOrder(
        SkuStock wishList,
        akka.actor.typed.ActorRef<com.saaenmadsen.shardworld.actors.company.ShardCompany.ShardCompanyCommand> buyer) implements CountryMarket.CountryMarketCommand {

    public static C_BuyOrder fromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_BuyOrder.class);
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
