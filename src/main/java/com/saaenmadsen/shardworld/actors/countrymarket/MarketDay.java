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
    public List<C_BuyB2BOrder> b2bBuyOrderList;
    public List<C_BuyPopOrder> popBuyOrderList;
    public int companiesDoneWithMarketDay;

    public StockListing getUnfulfilledOrders() {
        return unfulfilledOrders;
    }

    private StockListing unfulfilledOrders = StockListing.ofEmpty();
    private StockListing totalUnsoldGoods;
    private StockListing totalGoodsForSale = StockListing.ofEmpty();
    private MarketDailyReport marketDailyReport;


    public MarketDay(int dayId) {
        this(null, dayId);
    }

    public MarketDay(MarketDay marketDay, int dayId) {
        if (marketDay != null) {
            this.priceListDayStart = marketDay.newestPriceList;
            this.newestPriceList = marketDay.newestPriceList;
        } else {
            this.priceListDayStart = PriceList.ofDefault();
            this.newestPriceList = PriceList.ofDefault();
        }
        marketBooths = new ArrayList<>();
        b2bBuyOrderList = new ArrayList<>();
        popBuyOrderList = new ArrayList<>();

        companiesDoneWithMarketDay = 0;
        marketDailyReport = new MarketDailyReport(dayId);
        marketDailyReport.setPriceListDayStart(this.newestPriceList.duplicate());
    }

    public StockListing doShoppingAndReturnShoppingCart(StockListing wishList, MoneyBox buyersMoney) {
        StockListing shoppingCart = StockListing.ofEmpty();

        marketBooths.forEach(booth -> booth.performTradeAccordingToShoppingList(shoppingCart, wishList, newestPriceList, buyersMoney, marketDailyReport));

        StockListing myUnfulfilledOrders = wishList.createDuplicate();
        myUnfulfilledOrders.removeStockFromList(shoppingCart);
        this.unfulfilledOrders.addStockFromList(myUnfulfilledOrders);

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

    public List<C_BuyB2BOrder> getBuyOrderList() {
        return b2bBuyOrderList;
    }

    public int getCompaniesDoneWithMarketDay() {
        return companiesDoneWithMarketDay;
    }

    public void addBooth(MarketBooth sellersBooth) {
        this.marketBooths.add(sellersBooth);
        this.totalGoodsForSale.addStockFromList(sellersBooth.boothStock);
    }

    public void adjustPrices(Logger log) {
        totalUnsoldGoods = StockListing.ofEmpty();
        marketBooths.forEach(booth -> totalUnsoldGoods.addStockFromList(booth.boothStock));
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {
            int oldPrice = newestPriceList.getPrice(i);
            newestPriceList.setPrice(i, calculateNewPrice(oldPrice, totalGoodsForSale.getSkuCount(i), totalUnsoldGoods.getSkuCount(i), unfulfilledOrders.getSkuCount(i), calculateMaxPrice(i), calculateMinPrice(i)));
            log.debug("Price of " + StockKeepUnit.values()[i].getProductName() + " from " + oldPrice + "  to " + newestPriceList.getPrice(i));
        }
    }

    private int calculateMaxPrice(int skuId) {
        return StockKeepUnit.values()[skuId].getInitialPrice() * 2;
    }

    private int calculateMinPrice(int skuId) {
        return 1 + StockKeepUnit.values()[skuId].getInitialPrice() / 2;
    }

    public static int calculateNewPrice(int oldPrice, int amountForSale, int amountUnsold, int unfulfilledOrders, int maxPrice, int minPrice) {
        int newPrice = adjustDueToUnsoldGoods(oldPrice, amountForSale, amountUnsold);
        newPrice = adjustDueToUnfulfilledOrders(newPrice, amountForSale, unfulfilledOrders);
        newPrice = Math.min(newPrice, maxPrice);
        newPrice = Math.max(newPrice, minPrice);
        return newPrice;
    }

    private static int adjustDueToUnsoldGoods(int oldPrice, int amountForSale, int amountUnsold) {
        if (amountForSale == 0) {
            return oldPrice;
        }
        int percentUnsoldBucketsOfTen = (10 * amountUnsold) / (amountForSale);

        switch (percentUnsoldBucketsOfTen) {
            case 0: // 0-9% is unsold
                return oldPrice;
            case 1: // 10-19% is unsold
                return oldPrice;
            case 2: // 20-29% is unsold
                return oldPrice;
            case 3: // 30-39% is unsold
                return oldPrice;
            case 4: // 40-49% is unsold
                return (int) (oldPrice * 0.95);
            case 5: // 50-59% is unsold
                return (int) (oldPrice * 0.93);
            case 6: // 60-69% is unsold
                return (int) (oldPrice * 0.91);
            case 7: // 70-79% is unsold
                return (int) (oldPrice * 0.88);
            case 8: // 80-89% is unsold
                return (int) (oldPrice * 0.85);
            case 9: // 90-99% is unsold
                return (int) (oldPrice * 0.82);
            case 10: // 100% is unsold
                return (int) (oldPrice * 0.8);
        }
        throw new RuntimeException(String.format("Failed adjustDueToUnsoldGoods. oldPrice {}, amountForSale {}, amountUnsold {}", oldPrice, amountForSale, amountUnsold));
    }

    private static int adjustDueToUnfulfilledOrders(int oldPrice, int amountForSale, int unfulfilledOrders) {
        if (unfulfilledOrders == 0) {
            return oldPrice;
        }
        if (amountForSale == 0) {
            return (int) (oldPrice * 1.15);
        }
        int percentRequirementsFulfilledBucketsOfTen = (10 * unfulfilledOrders) / (10 * amountForSale);

        switch (percentRequirementsFulfilledBucketsOfTen) {
            case 0: // Unfulfilled are insignificant.
                return (int) (oldPrice * 1.07);
            case 1: // example: Unfulfilled is equal to fulfilled.
                return (int) (oldPrice * 1.1);
            case 2:
                return (int) (oldPrice * 1.12);
            case 3:
                return (int) (oldPrice * 1.14);
            case 4:
                return (int) (oldPrice * 1.16);
            case 5:
                return (int) (oldPrice * 1.18);
            case 6:
                return (int) (oldPrice * 1.20);
            case 7:
                return (int) (oldPrice * 1.22);
            case 8:
                return (int) (oldPrice * 1.24);
            case 9: // Example: Amount for sale was 10% of the requirements
                return (int) (oldPrice * 1.26);
            case 10:
                return (int) (oldPrice * 1.30);
        }
        return (int) (oldPrice * 1.35);
    }


    public MarketDailyReport createMarketDayClosedownReport() {
        marketDailyReport.setPriceListDayEnd(this.newestPriceList.duplicate());
        marketDailyReport.setForSaleList(this.totalGoodsForSale);
        marketDailyReport.setUnsoldGoods(this.totalUnsoldGoods);
        return this.marketDailyReport;
    }

    public boolean enoughBuyersHaveDoneTheirBusiness(int numberOfCompaniesToFinish, int numberOfPopGroupsToFinish) {
        return b2bBuyOrderList.size() >= numberOfCompaniesToFinish && popBuyOrderList.size() >= numberOfPopGroupsToFinish;
    }
}
