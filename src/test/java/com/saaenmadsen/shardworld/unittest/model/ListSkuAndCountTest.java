package com.saaenmadsen.shardworld.unittest.model;

import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.ListSkuAndCount;
import com.saaenmadsen.shardworld.modeltypes.SkuAndCount;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListSkuAndCountTest {
    @Test
    public void testListSkuAndCount() {
        ListSkuAndCount listSkuAndCount = new ListSkuAndCount();
        listSkuAndCount.add(new SkuAndCount(StockKeepUnit.APPLES, 1));
        listSkuAndCount.add(new SkuAndCount(StockKeepUnit.BRASS_PLATE, 1));

        List<StockKeepUnit> externalList = new ArrayList<>();
        externalList.add(StockKeepUnit.APPLES);

        assertFalse(listSkuAndCount.areAllIn(externalList));
        externalList.add(StockKeepUnit.BRASS_PLATE);
        assertTrue(listSkuAndCount.areAllIn(externalList));
    }
}
