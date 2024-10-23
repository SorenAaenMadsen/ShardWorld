package com.saaenmadsen.shardworld.unittest.company;

import com.saaenmadsen.shardworld.actors.countrymarket.C_SendSkuToMarketForSale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CSellOrderTest {

    @Test
    public void toAndFromJsonTest(){
            C_SendSkuToMarketForSale originalCommand = new C_SendSkuToMarketForSale(12,12);
        String json = originalCommand.toJson();
        C_SendSkuToMarketForSale parsed = C_SendSkuToMarketForSale.fromJson(json);

        assertEquals( originalCommand.count(), parsed.count());
        assertEquals( originalCommand.skuId(), parsed.skuId());

        C_SendSkuToMarketForSale differentCommand = new C_SendSkuToMarketForSale(12,14);
        assertNotEquals( originalCommand.count(), differentCommand.count());
        assertEquals( originalCommand.skuId(), differentCommand.skuId());
    }


}
