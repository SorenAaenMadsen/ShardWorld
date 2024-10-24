package com.saaenmadsen.shardworld.modeltypes;

import com.saaenmadsen.shardworld.constants.StockKeepUnit;

import java.util.Arrays;
import java.util.Objects;

public class StockListing {


    private int[] stock;

    public StockListing() {
        this.stock = new int[StockKeepUnit.values().length];
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {
            stock[i] = 0;
        }
    }

    private StockListing(StockListing skuStock) {
        this.stock = new int[skuStock.stock.length];
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {
            stock[i] = skuStock.stock[i];
        }
    }

    public int getSkuCount(int skuId) {
        return stock[skuId];
    }

    public void setSkuCount(int skuId, int newAmount) {
        stock[skuId] = newAmount;
    }

    /**
     * Substracts the amounts in the wishlist from this wishList, and returns the new wishList list.
     */
    public StockListing retrieve(StockListing wishList) {
        StockListing retrieved = new StockListing();
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

    /**
     * The customer comes with his shopping list, and his cart. We fulfill what we can, and he will continue his merry way.
     */
    public void serveCustomer(StockListing shoppingCart, StockListing shoppingList) {
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {

            int missingAmountInCart = shoppingList.getSkuCount(i)-shoppingCart.getSkuCount(i);
            if (missingAmountInCart>0) {
                int soldAmount = 0;
                if (missingAmountInCart <= stock[i]) {
                    soldAmount = missingAmountInCart;
                } else {
                    soldAmount = stock[i];
                }
                this.addStockAmount(i, -soldAmount);
                shoppingCart.addStockAmount(i, soldAmount);
            }
        }
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

    @Override
    public String toString() {
        return "SkuStock{" +
                "wishList=" + Arrays.toString(stock) +
                '}';
    }

    public int[] getStock() {
        return stock;
    }

    public void setStock(int[] stockOverride) {
        this.stock = stockOverride;
    }


    public StockListing createDuplicate() {
        return new StockListing(this);
    }

    public void addStockFromList(StockListing stockListing) {
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {
            stock[i] += stockListing.stock[i];
        }
    }
}
