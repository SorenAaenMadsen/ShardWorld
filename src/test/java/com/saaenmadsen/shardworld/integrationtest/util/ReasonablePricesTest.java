package com.saaenmadsen.shardworld.integrationtest.util;

import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.SkuAndCount;
import com.saaenmadsen.shardworld.statistics.PrintablePriceList;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class ReasonablePricesTest {

    private record SkuWithPrice(int newPrice, StockKeepUnit sku) {
    }

    @Test
    public void testThatSkuPricesAreResonable() {
        PriceList priceList = PriceList.ofMinusOnePrices();

        Set<StockKeepUnit> skusWithResolvedPrices = new HashSet<>();
        Set<StockKeepUnit> skuWherePricesAreNotResolved = new HashSet<>();
        skuWherePricesAreNotResolved.addAll(List.of(StockKeepUnit.values()));

        boolean weAreMakingProgress = true;
        while (weAreMakingProgress) {
            List<SkuWithPrice> skuWithPrice = new ArrayList<>();
            for (StockKeepUnit skuToMaybeResolve : skuWherePricesAreNotResolved) {
                for (Recipe recipe : Recipe.values()) {
                    if (recipeOutputsSku(recipe, skuToMaybeResolve)) {
                        // we have a recipe that produces the SKU without a price.
                        if (recipe.getInputs().areAllIn(skusWithResolvedPrices)) {
                            // And all inputs have their prices resolved.
                            if (recipe.getOutputs().size() == 1) {
                                // And this product is the only output of the recipe. TODO: Probably need to add an else clause to allow more than one output of a recipe!
                                int rawProductPrice = recipe.calculateRawProductPriceForProductionRun(priceList);
                                int workerSalary = recipe.getWorkTimeTimes10Minutes()*10;
                                int waitDaysFromProductionToAvailable = recipe.getCalenderWaitDaysFromProductionToAvailable()*10;
                                int amountProduced = recipe.getOutputs().skuAndCounts.getFirst().amount();
                                int newDesiredPrice = (int) ((rawProductPrice + workerSalary + waitDaysFromProductionToAvailable) * 1.1 / amountProduced);

                                assertTrue(skuToMaybeResolve.getProductName() + " [" + skuToMaybeResolve.getArrayId() + "] price resolved to " + newDesiredPrice + ". " +
                                                String.format("rawProductPrice=%s, workerSalary=%s, waitDaysFromProductionToAvailable=%s", rawProductPrice, workerSalary, waitDaysFromProductionToAvailable),
                                        newDesiredPrice > 0)
                                ;

                                skuWithPrice.add(new SkuWithPrice(newDesiredPrice, skuToMaybeResolve));
                            } else {
                                throw new RuntimeException("More than one output for "+recipe.name()+", this is not supported yet");
                            }
                        }
                    }
                }
            }

            if (skuWithPrice.isEmpty()) {
                weAreMakingProgress = false;
            }

            skuWithPrice.forEach(skuWithPriceElement -> {
                priceList.setPrice(skuWithPriceElement.sku.getArrayId(), skuWithPriceElement.newPrice);
                skusWithResolvedPrices.add(skuWithPriceElement.sku);
                skuWherePricesAreNotResolved.remove(skuWithPriceElement.sku);
            });

        }
        System.out.println(new PrintablePriceList(priceList));
        skusWithResolvedPrices.stream()
                .sorted(Comparator.comparing(sku->sku.getArrayId()))
                .map(sku->String.format("Sku [%s] %s price=%s", sku.getArrayId(), sku.getProductName(), priceList.getPrice(sku.getArrayId())))

                .forEach(System.out::println);

        for (StockKeepUnit resolvedSku : skusWithResolvedPrices) {
            assertThat(resolvedSku.getProductName() + " [" + resolvedSku.getArrayId() + "] does not have the correct initial price",
                    resolvedSku.getInitialPrice(),
                    equalTo(priceList.getPrice(resolvedSku.getArrayId())));
        }
    }


    private static boolean recipeOutputsSku(Recipe recipe, StockKeepUnit sku) {
        for (SkuAndCount output : recipe.getOutputs().skuAndCounts) {
            if (output.sku().getProductName().equals(sku.getProductName())) {
                return true;
            }
        }
        return false;
    }
}
