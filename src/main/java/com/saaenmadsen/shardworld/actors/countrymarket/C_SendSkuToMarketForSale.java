package com.saaenmadsen.shardworld.actors.countrymarket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public record C_SendSkuToMarketForSale(
        StockListing forSaleList,
        akka.actor.typed.ActorRef<com.saaenmadsen.shardworld.actors.company.ShardCompany.ShardCompanyCommand> seller) implements CountryMarket.CountryMarketCommand{

    public static C_SendSkuToMarketForSale fromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_SendSkuToMarketForSale.class);
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
