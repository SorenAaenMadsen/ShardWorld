package com.saaenmadsen.shardworld.actors.popgroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.modeltypes.PriceList;

public record C_RetailShopsOpenForBuyers(
        int dayId,
        PriceList priceList
    ) implements A_PopGroup.PopGroupCommand{

    public static C_RetailShopsOpenForBuyers fromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_RetailShopsOpenForBuyers.class);
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
