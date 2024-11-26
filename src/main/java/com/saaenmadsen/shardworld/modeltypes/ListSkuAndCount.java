package com.saaenmadsen.shardworld.modeltypes;

import com.saaenmadsen.shardworld.constants.StockKeepUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ListSkuAndCount {
    public List<SkuAndCount> skuAndCounts;



    public boolean isSkuInList(StockKeepUnit sku) {
        for (SkuAndCount output : skuAndCounts) {
            if(output.sku().getProductName().equals(sku.getProductName())){
                return true;
            }
        }
        return false;
    }

    private ListSkuAndCount(List<SkuAndCount> skuAndCounts) {
        this.skuAndCounts = skuAndCounts;
    }

    public ListSkuAndCount() {
        this.skuAndCounts = new ArrayList<>();
    }

    public Stream<SkuAndCount> stream() {
        return skuAndCounts.stream();
    }

    public void add(SkuAndCount skuAndCount) {
        skuAndCounts.add(skuAndCount);
    }

    public boolean areAllIn(Iterable<StockKeepUnit> skuList) {
        for (SkuAndCount skuAndCount : this.skuAndCounts) {
            boolean found = false;
            for (StockKeepUnit externalSku : skuList) {
                if (skuAndCount.sku().getProductName().equals(externalSku.getProductName())) {
                    found = true;
                }
            }
            if(!found){
                return false;
            }
        }
        return true;
    }

    public int size() {
        return skuAndCounts.size();
    }

    public void addAll(List<SkuAndCount> productProduced) {
        this.skuAndCounts.addAll(productProduced);
    }
}
