package com.saaenmadsen.shardworld.actors.countrymarket;

import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.ArrayList;
import java.util.List;

public class MarketDay {
    private PriceList newestPriceList;
    private PriceList priceListDayStart;

    public List<MarketBooth> marketBooths;
    public List<C_BuyOrder> buyOrderList;
    public int companiesDoneWithMarketDay;


    public MarketDay() {
        this(null);
    }

    public MarketDay(MarketDay marketDay) {
        if(marketDay != null) {
            this.priceListDayStart = marketDay.newestPriceList;
            this.newestPriceList = marketDay.newestPriceList;
        } else {
            this.priceListDayStart = new PriceList();
            this.newestPriceList = new PriceList();
        }
        marketBooths = new ArrayList<>();
        buyOrderList = new ArrayList<>();

        companiesDoneWithMarketDay = 0;
    }

    public StockListing doShoppingAndReturnShoppingCart(StockListing wishList, MoneyBox buyersMoney){
        StockListing shoppingCart = StockListing.createEmptyStockListing();

        marketBooths.forEach(booth->booth.performTradeAccordingToShoppingList(shoppingCart, wishList, newestPriceList, buyersMoney));
        return shoppingCart;
    }

    public PriceList getNewestPriceList() {
        return newestPriceList;
    }

    public PriceList getPriceListDayStart() {
        return priceListDayStart;
    }

    public List<MarketBooth> getMarketBooths() {
        return marketBooths;
    }

    public List<C_BuyOrder> getBuyOrderList() {
        return buyOrderList;
    }

    public int getCompaniesDoneWithMarketDay() {
        return companiesDoneWithMarketDay;
    }

    public void addBooth(MarketBooth sellersBooth) {
        this.marketBooths.add(sellersBooth);
    }
}
