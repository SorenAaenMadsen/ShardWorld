package com.saaenmadsen.shardworld.unittest.model;

import akka.actor.typed.ActorRef;
import com.saaenmadsen.shardworld.actors.company.A_ShardCompany;
import com.saaenmadsen.shardworld.actors.countrymarket.MarketBooth;
import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MerketBoothTest {
    @Test
    public void StockPurchaseSaleTest() {
        StockListing seller1 = StockListing.createEmptyStockListing();
        StockListing seller2 = StockListing.createEmptyStockListing();
        StockListing shoppingCart = StockListing.createEmptyStockListing();
        StockListing wishList = StockListing.createEmptyStockListing();



        seller1.setSkuCount(0, 10);
        seller1.setSkuCount(1, 10);
        seller2.setSkuCount(0, 10);
        seller2.setSkuCount(1, 10);

        MarketBooth booth1 = new MarketBooth(seller1, null);
        MarketBooth booth2 = new MarketBooth(seller2, null);

        wishList.setSkuCount(0, 12);
        wishList.setSkuCount(1, 8);
        MoneyBox customersMoney = new MoneyBox();
        customersMoney.addMoney(10000000);

        booth1.performTradeAccordingToShoppingList(shoppingCart, wishList, new PriceList(), customersMoney);
        booth2.performTradeAccordingToShoppingList(shoppingCart, wishList, new PriceList(), customersMoney);

        assertEquals(0, seller1.getSkuCount(0));
        assertEquals(2, seller1.getSkuCount(1));
        assertEquals(8, seller2.getSkuCount(0));
        assertEquals(10, seller2.getSkuCount(1));

        assertEquals(9999876, customersMoney.getMoney());
        assertEquals(114, booth1.getBoothRevenue().getMoney());
        assertEquals(10, booth2.getBoothRevenue().getMoney());

    }
}
