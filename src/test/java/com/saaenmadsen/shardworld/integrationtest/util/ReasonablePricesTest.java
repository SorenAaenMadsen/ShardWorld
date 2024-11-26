package com.saaenmadsen.shardworld.integrationtest.util;

import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.SkuAndCount;
import com.saaenmadsen.shardworld.statistics.PrintablePriceList;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                            int rawProductPrice = recipe.calculateRawProductPriceForProductionRun(priceList);
                            int workerSalary = recipe.getWorkTimeTimes10Minutes()*10;
                            int waitDaysFromProductionToAvailable = recipe.getCalenderWaitDaysFromProductionToAvailable()*10;
                            int amountProduced = recipe.getOutputs().skuAndCounts.getFirst().amount();

                            if (recipe.getOutputs().size() == 1) {
                                // And this product is the only output of the recipe.
                                int newDesiredPrice = (int) ((rawProductPrice + workerSalary + waitDaysFromProductionToAvailable) * 1.1 / amountProduced);

                                assertTrue(skuToMaybeResolve.getProductName() + " [" + skuToMaybeResolve.getArrayId() + "] price resolved to " + newDesiredPrice + ". " +
                                                String.format("rawProductPrice=%s, workerSalary=%s, waitDaysFromProductionToAvailable=%s", rawProductPrice, workerSalary, waitDaysFromProductionToAvailable),
                                        newDesiredPrice > 0)
                                ;

                                skuWithPrice.add(new SkuWithPrice(newDesiredPrice, skuToMaybeResolve));
                            } else {
                                Stream<SkuAndCount> outputsWithPriceAlreadyResolved = recipe.getOutputs().stream().filter(output -> skusWithResolvedPrices.contains(output.sku()));
                                int valueOfOutputsWithResolvedPrice = outputsWithPriceAlreadyResolved.map(output -> output.amount() * priceList.getPrice(output.sku())).mapToInt(value -> value).sum();
                                // Now we assume the remaining items are equally valuable. Not really a good decision :)
                                List<SkuAndCount> outputsWithUnresolvedPrice = recipe.getOutputs().stream().filter(output -> !skusWithResolvedPrices.contains(output.sku())).collect(Collectors.toUnmodifiableList());
                                int totalAmountOfUnpricedProducts = outputsWithUnresolvedPrice.stream().map(output -> output.amount()).mapToInt(value -> value).sum();

                                int newDesiredPrice = (int) ((rawProductPrice + workerSalary + waitDaysFromProductionToAvailable) * 1.1 / totalAmountOfUnpricedProducts);

                                for (SkuAndCount skuAndCount : outputsWithUnresolvedPrice) {
                                    assertTrue(skuAndCount.sku().getProductName() + " [" + skuAndCount.sku().getArrayId() + "] price resolved to " + newDesiredPrice + ". " +
                                                    String.format("rawProductPrice=%s, workerSalary=%s, waitDaysFromProductionToAvailable=%s", rawProductPrice, workerSalary, waitDaysFromProductionToAvailable),
                                            newDesiredPrice > 0)
                                    ;

                                    skuWithPrice.add(new SkuWithPrice(newDesiredPrice, skuAndCount.sku()));
                                }
//                                  throw new RuntimeException("More than one output for "+recipe.name()+", this is not supported yet");
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
