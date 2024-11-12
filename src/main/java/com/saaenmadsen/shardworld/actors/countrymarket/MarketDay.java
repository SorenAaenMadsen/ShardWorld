package com.saaenmadsen.shardworld.actors.countrymarket;

import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MarketDay {
    private PriceList newestPriceList;
    private PriceList priceListDayStart;

    public List<MarketBooth> marketBooths;
    public List<C_BuyOrder> buyOrderList;
    public int companiesDoneWithMarketDay;

    StockListing totalUnsoldGoods;
    StockListing totalGoodsForSale = StockListing.createEmptyStockListing();


    public MarketDay() {
        this(null);
    }

    public MarketDay(MarketDay marketDay) {
        if (marketDay != null) {
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

    public StockListing doShoppingAndReturnShoppingCart(StockListing wishList, MoneyBox buyersMoney) {
        StockListing shoppingCart = StockListing.createEmptyStockListing();

        marketBooths.forEach(booth -> booth.performTradeAccordingToShoppingList(shoppingCart, wishList, newestPriceList, buyersMoney));
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
        this.totalGoodsForSale.addStockFromList(sellersBooth.boothStock);
    }

    public void adjustPricesAccordingToUnsoldGoods(Logger log) {
        totalUnsoldGoods = StockListing.createEmptyStockListing();
        marketBooths.forEach(booth -> totalUnsoldGoods.addStockFromList(booth.boothStock));
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {
            int oldPrice = newestPriceList.getPrice(i);
            newestPriceList.setPrice(i, calculateNewPrice(oldPrice, totalGoodsForSale.getSkuCount(i), totalUnsoldGoods.getSkuCount(i)));
            log.info("Price of " + StockKeepUnit.values()[i].getProductName() + " from "+ oldPrice+"  to " + newestPriceList.getPrice(i));
        }
    }

    public static int calculateNewPrice(int oldPrice, int amountForSale, int amountUnsold) {
        if(amountForSale==0){
            return oldPrice;
        }
        int percentUnsoldBucketsOfTen = (10 * amountUnsold) / (10 * amountForSale);

        switch (percentUnsoldBucketsOfTen) {
            case 0:
                return (int) (oldPrice * 0.8); // Nothing or less than 10% sold
            case 1:
                return (int) (oldPrice * 0.82); // 10-19 percent sold
            case 2:
                return (int) (oldPrice * 0.84); // 20-29 percent sold
            case 3:
                return (int) (oldPrice * 0.87); // 30-39 percent sold
            case 4:
                return (int) (oldPrice * 0.90); // 40-49 percent sold
            case 5:
                return (int) (oldPrice * 0.93); // 50-59 percent sold
            case 6:
                return (int) (oldPrice * 0.97); // 60-69 percent sold
            case 7:
                return oldPrice; // 70-79 percent sold
            case 8:
                return oldPrice; // 80-89 percent sold
            case 9:
                return (int) (oldPrice * 1.02); // 90-99 percent sold
            case 10:
                return (int) (oldPrice * 1.10); // The full 100 percent sold
        }
        throw new RuntimeException(String.format("Failed to calculate new price. oldPrice {}, amountForSale {}, amountUnsold {}", oldPrice, amountForSale, amountUnsold));
    }
}
