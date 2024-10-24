package com.saaenmadsen.shardworld.recipechoice;

import com.saaenmadsen.shardworld.modeltypes.SkuStock;

public record ProductionImpactReport(
        int maxProductionBeforeRunningOutOfTimeOrMaterials,
        int leftOverWorkTime,
        SkuStock usedRawMaterial,
        SkuStock leftOverStock
) {
}
