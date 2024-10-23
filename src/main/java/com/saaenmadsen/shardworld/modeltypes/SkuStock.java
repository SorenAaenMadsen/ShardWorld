package com.saaenmadsen.shardworld.modeltypes;

import com.saaenmadsen.shardworld.constants.StockKeepUnit;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SkuStock {

    private final int[] stock;
    public SkuStock() {
        this.stock = new int[StockKeepUnit.values().length];
        for(int i=0;i<StockKeepUnit.values().length;++i){
            stock[i] = 0;
        }
    }



    public int getStock(StockKeepUnit sku){
        return stock[sku.getArrayId()];
    }

    public int getStock(int skuId){
        return stock[skuId];
    }

    public IntStream stream(){
        return Arrays.stream(stock);
    }


    public int[] getArray() {
        return stock;
    }
}
