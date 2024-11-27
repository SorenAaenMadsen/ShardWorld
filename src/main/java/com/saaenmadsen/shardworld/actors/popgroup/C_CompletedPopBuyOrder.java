package com.saaenmadsen.shardworld.actors.popgroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public record C_CompletedPopBuyOrder(
        StockListing boughtStuff,
        MoneyBox unspentMoney
    ) implements A_PopGroup.PopGroupCommand{

    public static C_CompletedPopBuyOrder fromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_CompletedPopBuyOrder.class);
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
