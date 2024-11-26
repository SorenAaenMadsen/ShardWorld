package com.saaenmadsen.shardworld.unittest.company;

import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.CompanyInformationBuilder;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyType;
import com.saaenmadsen.shardworld.actors.company.direction.ProductionPlanningAndExecution;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettingsBuilder;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ProductionTest {
    @Test
    public void testWithPartialProduction() {
        StockKeepUnit producedSku = Recipe.SHAFT_FURNACE_CRUCIBLE_HARD_STEEL_SMELTING.getOutputs().skuAndCounts.getFirst().sku();
        StockListing warehouse = StockListing.createMaxedOutStockListing();
        warehouse.setSkuCount(producedSku.getArrayId(), 0);

        CompanyInformation companyInformation = CompanyInformationBuilder
                .ofWorldDefault("testcompany", WorldSettingsBuilder.ofDefault().build())
                .withCompanyType(CompanyType.PRODUCTION)
                .withWarehouse(warehouse)
                .withWorkers(10)
                .withKnownRecipe(new KnownRecipe(Recipe.SHAFT_FURNACE_CRUCIBLE_HARD_STEEL_SMELTING, 1000))
                .build();
        CompanyDailyReport report = new CompanyDailyReport("testcompany", 1);
        ProductionPlanningAndExecution productionPlanningAndExecution1 = new ProductionPlanningAndExecution(companyInformation, report, Optional.empty());
        Optional<ProductionPlanningAndExecution.PartialProduction> partialProduction1 = productionPlanningAndExecution1.execute();

        assertThat("Because the recipe takes very long, a partial production is run.",
                partialProduction1.isPresent(),
                equalTo(true));
        assertThat("",
                partialProduction1.get().alreadyAllocatedTime(),
                equalTo(80));
        assertThat("",
                partialProduction1.get().recipe(),
                equalTo(Recipe.SHAFT_FURNACE_CRUCIBLE_HARD_STEEL_SMELTING));

        ProductionPlanningAndExecution productionPlanningAndExecution2 = new ProductionPlanningAndExecution(companyInformation, report, partialProduction1);
        Optional<ProductionPlanningAndExecution.PartialProduction> partialProduction2 = productionPlanningAndExecution2.execute();
        assertThat("",
                partialProduction2.get().alreadyAllocatedTime(),
                equalTo(160));

    }

    @Test
    public void testPartialProductionFinishedResultsInProducts() {
        StockKeepUnit producedSku = Recipe.SHAFT_FURNACE_CRUCIBLE_HARD_STEEL_SMELTING.getOutputs().skuAndCounts.getFirst().sku();
        int totalNeededWorktime = Recipe.SHAFT_FURNACE_CRUCIBLE_HARD_STEEL_SMELTING.getWorkTimeTimes10Minutes();
        StockListing warehouse = StockListing.ofEmpty();
        Optional<ProductionPlanningAndExecution.PartialProduction> partialProduction = Optional.of(new ProductionPlanningAndExecution.PartialProduction(Recipe.SHAFT_FURNACE_CRUCIBLE_HARD_STEEL_SMELTING, totalNeededWorktime - 20));

        CompanyInformation companyInformation = CompanyInformationBuilder
                .ofWorldDefault("testcompany", WorldSettingsBuilder.ofDefault().build())
                .withCompanyType(CompanyType.PRODUCTION)
                .withWarehouse(warehouse)
                .withWorkers(10)
                .withKnownRecipe(new KnownRecipe(Recipe.SHAFT_FURNACE_CRUCIBLE_HARD_STEEL_SMELTING, 1000))
                .build();
        CompanyDailyReport report = new CompanyDailyReport("testcompany", 1);

        assertThat("Before production, no har steel exists.",
                warehouse.getSkuCount(producedSku.getArrayId()),
                equalTo(0));

        ProductionPlanningAndExecution productionPlanningAndExecution1 = new ProductionPlanningAndExecution(companyInformation, report, partialProduction);
        productionPlanningAndExecution1.execute();

        assertThat("Because the recipe takes very long, a partial production is run.",
                warehouse.getSkuCount(producedSku.getArrayId()),
                equalTo(Recipe.SHAFT_FURNACE_CRUCIBLE_HARD_STEEL_SMELTING.getOutputs().skuAndCounts.getFirst().amount()));
    }
}
