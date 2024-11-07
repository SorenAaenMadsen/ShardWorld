package com.saaenmadsen.shardworld.statistics;

import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.HashMap;
import java.util.Map;

public class PrintableStockListing {
    Map<String, Integer> printableStockListing = new HashMap<>();

    public PrintableStockListing(StockListing stockListing) {
        for (StockKeepUnit sku : StockKeepUnit.values()) {
            if(stockListing.getStockAmount(sku)>0) {
                printableStockListing.put(sku.getProductName(), stockListing.getStockAmount(sku));
            }
        }
    }
}
