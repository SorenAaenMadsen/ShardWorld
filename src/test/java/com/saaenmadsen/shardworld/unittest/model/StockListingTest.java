package com.saaenmadsen.shardworld.unittest.model;

import com.saaenmadsen.shardworld.modeltypes.StockListing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockListingTest {
    @Test
    public void StockPurchaseSaleTest() {
        StockListing seller1 = new StockListing();
        StockListing seller2 = new StockListing();
        StockListing shoppingCart = new StockListing();
        StockListing wishList = new StockListing();

        seller1.setSkuCount(0, 10);
        seller1.setSkuCount(1, 10);
        seller2.setSkuCount(0, 10);
        seller2.setSkuCount(1, 10);

        wishList.setSkuCount(0, 12);
        wishList.setSkuCount(1, 8);

        seller1.serveCustomer(shoppingCart, wishList);
        seller2.serveCustomer(shoppingCart, wishList);

        assertEquals(0, seller1.getSkuCount(0));
        assertEquals(2, seller1.getSkuCount(1));
        assertEquals(8, seller2.getSkuCount(0));
        assertEquals(10, seller2.getSkuCount(1));

    }
}
