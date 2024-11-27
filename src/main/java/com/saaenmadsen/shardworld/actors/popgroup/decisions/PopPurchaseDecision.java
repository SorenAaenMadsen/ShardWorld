package com.saaenmadsen.shardworld.actors.popgroup.decisions;

import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public class PopPurchaseDecision {
    private StockListing wishList = StockListing.ofEmpty();

    public PopPurchaseDecision() {
        wishList.addStockAmount(StockKeepUnit.APPLES, 10);
        wishList.addStockAmount(StockKeepUnit.FIREWOOD_KG, 10);
        wishList.addStockAmount(StockKeepUnit.POTATOES, 10);
        wishList.addStockAmount(StockKeepUnit.MEAT_CHICKEN, 10);
        wishList.addStockAmount(StockKeepUnit.MEET_BEEF, 10);
        wishList.addStockAmount(StockKeepUnit.MEET_PORK, 10);
        wishList.addStockAmount(StockKeepUnit.BERRIES, 10);
        wishList.addStockAmount(StockKeepUnit.ONIONS, 10);
    }

    public static StockListing makeDecision() {
        return new PopPurchaseDecision().decide();
    }

    public StockListing decide(){
        return wishList;
    };
}
