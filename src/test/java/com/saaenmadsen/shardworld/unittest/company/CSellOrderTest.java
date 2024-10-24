package com.saaenmadsen.shardworld.unittest.company;

import com.saaenmadsen.shardworld.actors.countrymarket.C_SendSkuToMarketForSale;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CSellOrderTest {

    @Test
    public void toAndFromJsonTest() {
        StockListing originalStock = new StockListing();
        originalStock.setSkuCount(0, 14);
        originalStock.setSkuCount(1, 18);

        C_SendSkuToMarketForSale originalCommand = new C_SendSkuToMarketForSale(originalStock, null);
        String json = originalCommand.toJson();
        C_SendSkuToMarketForSale parsed = C_SendSkuToMarketForSale.fromJson(json);

        assertEquals(originalCommand.forSaleList(), parsed.forSaleList());

        StockListing differentStock = new StockListing();
        originalStock.setSkuCount(0, 64);
        originalStock.setSkuCount(1, 564);
        C_SendSkuToMarketForSale differentCommand = new C_SendSkuToMarketForSale(differentStock, null);
        assertNotEquals(originalCommand.forSaleList(), differentCommand.forSaleList());
    }


}
