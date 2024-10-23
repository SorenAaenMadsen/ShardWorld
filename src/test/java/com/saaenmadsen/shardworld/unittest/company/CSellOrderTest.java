package com.saaenmadsen.shardworld.unittest.company;

import com.saaenmadsen.shardworld.actors.countrymarket.C_SellOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CSellOrderTest {

    @Test
    public void toAndFromJsonTest(){
            C_SellOrder originalCommand = new C_SellOrder(12,12);
        String json = originalCommand.toJson();
        C_SellOrder parsed = C_SellOrder.fromJson(json);

        assertEquals( originalCommand.count(), parsed.count());
        assertEquals( originalCommand.skuId(), parsed.skuId());

        C_SellOrder differentCommand = new C_SellOrder(12,14);
        assertNotEquals( originalCommand.count(), differentCommand.count());
        assertEquals( originalCommand.skuId(), differentCommand.skuId());
    }


}
