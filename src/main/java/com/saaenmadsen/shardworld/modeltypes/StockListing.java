package com.saaenmadsen.shardworld.modeltypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StockListing {
    private int[] stock;

    private StockListing(int initialVal) {
        this.stock = new int[StockKeepUnit.values().length];
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {
            stock[i] = initialVal;
        }
    }

    private StockListing(StockListing skuStock) {
        this.stock = new int[skuStock.stock.length];
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {
            stock[i] = skuStock.stock[i];
        }
    }

    /**
     * Constructor for serialization only.
     */
    private StockListing() {
    }

    public int[] getStock() {
        return stock;
    }
    public void setStock(int[] stockOverride) {
        this.stock = stockOverride;
    }

    public static StockListing ofEmpty() {
        return new StockListing(new StockListing(0));
    }

    public static StockListing createMaxedOutStockListing() {
        return new StockListing(Integer.MAX_VALUE);
    }

    public StockListing createDuplicate() {
        return new StockListing(this);
    }

    @JsonIgnore
    public int getSkuCount(int skuId) {
        return stock[skuId];
    }

    @JsonIgnore
    public void setSkuCount(int skuId, int newAmount) {
        stock[skuId] = newAmount;
    }

    /**
     * Substracts the amounts in the wishlist from this wishList, and returns the new wishList list.
     */
    public StockListing retrieve(StockListing wishList) {
        StockListing retrieved = StockListing.ofEmpty();
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {
            int actualAmount = 0;
            if (wishList.getSkuCount(i) <= stock[i]) {
                actualAmount = wishList.getSkuCount(i);
            } else {
                actualAmount = stock[i];
            }
            stock[i] = stock[i] - actualAmount;
            retrieved.setSkuCount(i, actualAmount);
        }
        return retrieved;
    }


    public void removeStockAmount(int skuId, int addAmount) {
        this.addStockAmount(skuId, -addAmount);
    }

    public void addStockAmount(int skuId, int addAmount) {
        stock[skuId] += addAmount;
        if(stock[skuId]<0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockListing stockListing)) return false;
        return Objects.deepEquals(stock, stockListing.stock);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(stock);
    }



    public void addStockFromList(StockListing stockListing) {
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {
            stock[i] += stockListing.stock[i];
        }
    }

    public void removeStockFromList(StockListing stockListing) {
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {
            stock[i] -= stockListing.stock[i];
            if(stock[i]<0) {
                throw new IllegalArgumentException("Cannot remove stock as it would be negative");
            }
        }
    }

    @JsonIgnore
    public int getStockAmount(StockKeepUnit stockKeepUnit) {
        return this.stock[stockKeepUnit.getArrayId()];
    }

    public boolean hasStock(Recipe.SkuAndCount toolRequirement) {
        int myStock = this.getSkuCount(toolRequirement.sku().getArrayId());
        return myStock>=toolRequirement.amount();
    }

    public boolean hasStock(List<Recipe.SkuAndCount> listOfRequirements) {
        return listOfRequirements.stream().allMatch(req->this.hasStock(req));
    }

    public void removeStockFromList(List<Recipe.SkuAndCount> toRemoveList) {
        if(!hasStock(toRemoveList)) {
            throw new IllegalArgumentException("Cannot remove stock as it would be negative");
        }
        for (Recipe.SkuAndCount skuAndCounToRemove : toRemoveList) {
            int skuId = skuAndCounToRemove.sku().getArrayId();
            this.stock[skuId]-=skuAndCounToRemove.amount();
        }
    }
}
