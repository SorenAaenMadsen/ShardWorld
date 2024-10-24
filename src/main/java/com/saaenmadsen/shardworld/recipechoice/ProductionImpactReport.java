package com.saaenmadsen.shardworld.recipechoice;

import com.saaenmadsen.shardworld.modeltypes.StockListing;

public record ProductionImpactReport(
        int maxProductionBeforeRunningOutOfTimeOrMaterials,
        int leftOverWorkTime,
        StockListing usedRawMaterial,
        StockListing leftOverStock
) {
}
