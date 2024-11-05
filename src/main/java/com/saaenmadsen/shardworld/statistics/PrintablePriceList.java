package com.saaenmadsen.shardworld.statistics;

import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.HashMap;
import java.util.Map;

public class PrintablePriceList {
    Map<String, Integer> printableStockListing = new HashMap<>();

    public PrintablePriceList(PriceList priceList) {
        for (StockKeepUnit sku : StockKeepUnit.values()) {
            printableStockListing.put(sku.getProductName(), priceList.getPrice(sku));
        }
    }
}
