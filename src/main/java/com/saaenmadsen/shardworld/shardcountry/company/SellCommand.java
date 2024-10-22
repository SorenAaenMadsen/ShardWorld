package com.saaenmadsen.shardworld.shardcountry.company;

public record SellCommand(
        int skuId,
        int count
    ) {

    public static SellCommand fromJson(String json){
        return new SellCommand(2,2);
    }

    @Override
    public String toString() {
        return "SellCommand{" +
                "SKUID='" + skuId + '\'' +
                ", count='" + count + '\'' +
                "}";
    }

    public String toJson() {
        return this.toString();
    }
}
