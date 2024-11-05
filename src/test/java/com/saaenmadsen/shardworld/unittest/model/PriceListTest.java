package com.saaenmadsen.shardworld.unittest.model;

import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class PriceListTest {

    @Test
    public void stockKeepUnitBasicTest() {
        int i = 0;
        for (StockKeepUnit sku : StockKeepUnit.values()) {
            assertThat("Array ID should be the number it has in the enum list also",
                    sku.getArrayId(),
                    Matchers.equalTo(i++));
        }
    }

    @Test
    public void testPriceListSimple() {
        PriceList priceList = new PriceList();


//        for(int i=0;i<StockKeepUnit.values().length;++i){
//            assertThat("Array ID should be the number it has in the enum list also",
//                    StockKeepUnit.)
//        }
    }
}
