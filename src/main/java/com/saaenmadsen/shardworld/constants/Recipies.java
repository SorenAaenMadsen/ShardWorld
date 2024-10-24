package com.saaenmadsen.shardworld.constants;

public enum Recipies {
    WOOD_SAWING (0, "Wood Sawing", "Wood, kg", "18", "Using a manual saw, cured timber is cut into blocks of required size", "Timber, raw, kg", "20", "", "", "0", "36", "Woodworker_5", ""),
    PRIMITIVE_WOODEN_SHUE (1, "Primitive Wooden Shue", "Shue, Wooden", "1", "Making a shue of one piece of wood, using only what tools can be found in nature", "Wood, kg", "", "", "", "0", "24", "Shuemaker_1", ""),
    GATHER_RAW_TIMBER (2, "Gather Raw Timber", "Timber, Raw", "10", "Felling trees in the forest, using only what tools can be found in nature", "", "", "", "", "0", "3", "Forester_1", ""),
    PRIMITIVE_TIMBER_CURING (3, "Primitive Timber Curing", "Timber, Cured", "10", "Drying wood in the most primitive way, building a small shelter around it while it dries.", "Timber, raw, kg", "15", "", "", "180", "2", "Forester_3", ""),
    GATHER_CURED_WOOD (4, "Gather Cured Wood", "Wood, kg", "5", "Finding cured blocks of timber amoung deadwood in the forest, and forming into blocks with only what tools can be found in nature", "", "", "", "", "0", "10", "Forester_1", ""),
    GATHER_FIREWOOD (5, "Gather Firewood", "Firewood, kg", "10", "Walking around to gather firewood ", "", "", "", "", "0", "3", "Forester_1", "");



    Recipies(int recipeId, String recipeName, String productProduced, String producedAmount, String processDescription, String input_1, String input_1_amount, String input_2, String input_2_amount, String calenderWaitTimeFromProductionToAvailable, String workTime_times10Minutes, String skillLevel_1, String skillLevel_2) {
    }
}
