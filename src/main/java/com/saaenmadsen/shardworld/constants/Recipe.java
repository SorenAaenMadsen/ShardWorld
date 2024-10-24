package com.saaenmadsen.shardworld.constants;

import com.saaenmadsen.shardworld.modeltypes.PriceList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Recipe {
    WOOD_SAWING (0, "Wood Sawing", "Wood, kg", "18", "Using a manual saw, cured timber is cut into blocks of required size", "Timber, raw, kg", "20", "", "", "0", "36", "Woodworker_5", ""),
    PRIMITIVE_WOODEN_SHUE (1, "Primitive Wooden Shue", "Pair of Shues, Wooden", "1", "Making a shue of one piece of wood, using only what tools can be found in nature", "Wood, kg", "2", "", "", "0", "24", "Shuemaker_1", ""),
    GATHER_RAW_TIMBER (2, "Gather Raw Timber", "Timber, raw, kg", "10", "Felling trees in the forest, using only what tools can be found in nature", "", "", "", "", "0", "3", "Forester_1", ""),
    PRIMITIVE_TIMBER_CURING (3, "Primitive Timber Curing", "Timber, cured, kg", "10", "Drying wood in the most primitive way, building a small shelter around it while it dries.", "Timber, raw, kg", "15", "", "", "180", "2", "Forester_3", ""),
    GATHER_CURED_WOOD (4, "Gather Cured Wood", "Wood, kg", "5", "Finding cured blocks of timber amoung deadwood in the forest, and forming into blocks with only what tools can be found in nature", "", "", "", "", "0", "10", "Forester_1", ""),
    GATHER_FIREWOOD (5, "Gather Firewood", "Firewood, kg", "10", "Walking around to gather firewood ", "", "", "", "", "0", "3", "Forester_1", "");


    public record SkuAndCount(StockKeepUnit sku, int amount){
        public static Optional<SkuAndCount> fromStrings(String productName, String amount){
            if(productName.isEmpty()) return Optional.empty();
            return Optional.of(new SkuAndCount(StockKeepUnit.getByProductName(productName), Integer.parseInt(amount)));
        }
    };
    private final List<SkuAndCount> inputs = new ArrayList<>();
    private final List<SkuAndCount> outputs = new ArrayList<>();

    private final int recipeId;
    private final String recipeName;

    private final String processDescription;

    private final int calenderWaitTimeFromProductionToAvailable;
    private final int workTimeTimes10Minutes;
//    private final int skillLevel1;
//    private final int skillLevel2;



    Recipe(int recipeId, String recipeName, String productProduced, String producedAmount, String processDescription, String input_1, String input_1_amount, String input_2, String input_2_amount, String calenderWaitTimeFromProductionToAvailable, String workTime_times10Minutes, String skillLevel_1, String skillLevel_2) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;

        Optional<SkuAndCount> output1 = SkuAndCount.fromStrings(productProduced, producedAmount);
        output1.ifPresent(outputs::add);

        this.processDescription = processDescription;
        Optional<SkuAndCount> input1 = SkuAndCount.fromStrings(input_1, input_1_amount);
        input1.ifPresent(inputs::add);
        Optional<SkuAndCount> input2 = SkuAndCount.fromStrings(input_2, input_2_amount);
        input2.ifPresent(inputs::add);

        this.calenderWaitTimeFromProductionToAvailable = Integer.parseInt(calenderWaitTimeFromProductionToAvailable);
        workTimeTimes10Minutes = Integer.parseInt(workTime_times10Minutes);

//        skillLevel1 = Integer.parseInt(skillLevel_1);
//        skillLevel2 = Integer.parseInt(skillLevel_2);
    }

    public int calculateProfitPrWorkTenMin(PriceList priceList){
        Integer expenses = inputs.stream().map(skuAndCount -> priceList.getPrice(skuAndCount.sku.getArrayId()) * skuAndCount.amount).mapToInt(Integer::intValue).sum();
        Integer valueCreated = outputs.stream().map(skuAndCount -> priceList.getPrice(skuAndCount.sku.getArrayId()) * skuAndCount.amount).mapToInt(Integer::intValue).sum();
        return (valueCreated-expenses)/workTimeTimes10Minutes;
    }

}
