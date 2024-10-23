package com.saaenmadsen.shardworld.unittest.model;

import com.saaenmadsen.shardworld.modeltypes.SkuStock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkuStockTest {
    @Test
    public void StockPurchaseSaleTest() {
        SkuStock seller1 = new SkuStock();
        SkuStock seller2 = new SkuStock();
        SkuStock shoppingCart = new SkuStock();
        SkuStock wishList = new SkuStock();

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
