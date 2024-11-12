package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaenmadsen.shardworld.ShardWorld;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintableStockListing {
    Map<String, Integer> printableStockListing = new HashMap<>();

    public PrintableStockListing(StockListing stockListing) {
        for (StockKeepUnit sku : StockKeepUnit.values()) {
            if(stockListing.getStockAmount(sku)>0) {
                printableStockListing.put(sku.getProductName(), stockListing.getStockAmount(sku));
            }
        }
    }

    public Stream<Map.Entry<String, Integer>> stream(){
        return printableStockListing.entrySet().stream();
    }

    @JsonIgnore
    public List<ShardWorld.DataPoint> getAsDataPointsForWebGraph() {
        return this.stream().map(stock -> new ShardWorld.DataPoint(stock.getKey(), stock.getValue())).collect(Collectors.toUnmodifiableList());
    }
}
