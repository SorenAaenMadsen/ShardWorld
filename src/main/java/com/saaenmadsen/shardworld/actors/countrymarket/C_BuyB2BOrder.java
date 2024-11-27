package com.saaenmadsen.shardworld.actors.countrymarket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saaenmadsen.shardworld.actors.company.A_ShardCompany;
import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public record C_BuyB2BOrder(
        StockListing wishList,
        MoneyBox moneyBox,
        akka.actor.typed.ActorRef<A_ShardCompany.ShardCompanyCommand> buyer,
        String buyerCompanyId
) implements A_CountryMarket.CountryMarketCommand {

    public static C_BuyB2BOrder fromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, C_BuyB2BOrder.class);
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
