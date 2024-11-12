package com.saaenmadsen.shardworld.modeltypes;

import com.saaenmadsen.shardworld.constants.StockKeepUnit;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PriceList implements Cloneable {
    private int[] prices;

    public PriceList() {
        this.prices = new int[StockKeepUnit.values().length];
        Arrays.stream(StockKeepUnit.values()).forEach(sku->this.prices[sku.getArrayId()] = sku.getInitialPrice());
    }

    public int getPrice(StockKeepUnit sku){
        return prices[sku.getArrayId()];
    }

    public int getPrice(int skuId){
        return prices[skuId];
    }

    public void setPrice(int skuId, int price){
        prices[skuId] = price;
    }

    public IntStream stream(){
        return Arrays.stream(prices);
    }

    public int[] getArray() {
        return prices;
    }

    public PriceList duplicate(){
        PriceList copy = new PriceList();
        copy.prices = this.prices.clone();
        return copy;
    }
}
