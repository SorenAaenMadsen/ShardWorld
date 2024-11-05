package com.saaenmadsen.shardworld.actors.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.actors.countrymarket.A_CountryMarket;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public record C_SendUnsoldSkuBackToSeller(
        StockListing unsoldGoods,
        akka.actor.typed.ActorRef<A_CountryMarket.CountryMarketCommand> market) implements A_ShardCompany.ShardCompanyCommand{

    public static C_SendUnsoldSkuBackToSeller fromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_SendUnsoldSkuBackToSeller.class);
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
