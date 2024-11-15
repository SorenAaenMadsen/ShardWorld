package com.saaenmadsen.shardworld.constants;

import com.saaenmadsen.shardworld.modeltypes.ListSkuAndCount;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.SkuAndCount;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import com.saaenmadsen.shardworld.recipechoice.ProductionImpactReport;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

public enum Recipe {
    WOOD_SAWING(0, "Wood Sawing", "Wood, kg", "18", "Using a manual saw, cured timber is cut into blocks of required size", "Timber, raw, kg", "20", "Null", "0", "Null", "0", "0", "36", "Woodworker_5", "", "", "Iron tools", "1", "Null", "0", "Null", "0"),
    PRIMITIVE_WOODEN_SHUE(1, "Primitive Wooden Shue", "Pair of Shues, Wooden", "1", "Making a shue of one piece of wood, using only what tools can be found in nature", "Wood, kg", "2", "Null", "0", "Null", "0", "0", "24", "Shuemaker_1", "", "", "Stone tools", "3", "Null", "0", "Null", "0"),
    GATHER_RAW_TIMBER(2, "Gather Raw Timber", "Timber, raw, kg", "10", "Felling trees in the forest, using only what tools can be found in nature", "Null", "0", "Null", "0", "Null", "0", "0", "3", "Forester_1", "", "", "", "", "Null", "0", "Null", "0"),
    PRIMITIVE_TIMBER_CURING(3, "Primitive Timber Curing", "Timber, cured, kg", "10", "Drying wood in the most primitive way, building a small shelter around it while it dries.", "Timber, raw, kg", "15", "Null", "0", "Null", "0", "180", "2", "Forester_3", "", "", "Wood tools", "4", "Null", "0", "Null", "0"),
    GATHER_CURED_WOOD(4, "Gather Cured Wood", "Wood, kg", "5", "Finding cured blocks of timber amoung deadwood in the forest, and forming into blocks with only what tools can be found in nature", "Null", "0", "Null", "0", "Null", "0", "0", "10", "Forester_1", "", "", "Null", "0", "Null", "0", "Null", "0"),
    GATHER_FIREWOOD(5, "Gather Firewood", "Firewood, kg", "10", "Walking around to gather firewood ", "Null", "0", "Null", "0", "Null", "0", "0", "3", "Forester_1", "", "", "Null", "0", "Null", "0", "Null", "0"),
    GATHER_IRON_ORE(6, "Gather iron ore", "Iron Ore", "1", "Walking around to gather iron ore. Ore is expected to be 10% iron.", "Null", "0", "Null", "0", "Null", "0", "0", "12", "Miner_1", "", "", "Null", "0", "Null", "0", "Null", "0"),
    GATHER_COPPER_ORE(7, "Gather copper ore", "Copper Ore", "1", "Walking around to gather copper ore", "Null", "0", "Null", "0", "Null", "0", "0", "18", "Miner_1", "", "", "Null", "0", "Null", "0", "Null", "0"),
    GATHER_TIN_ORE(8, "Gather tin ore", "Tin Ore", "1", "Walking around to gather tin ore", "Null", "0", "Null", "0", "Null", "0", "0", "24", "Miner_1", "", "", "Null", "0", "Null", "0", "Null", "0"),
    GATHER_COAL(9, "Gather coal", "Coal, Mined", "1", "Walking around to gather coal", "Null", "0", "Null", "0", "Null", "0", "0", "16", "Miner_1", "", "", "Null", "0", "Null", "0", "Null", "0"),
    PRIMITIVE_COPPER_SMELTING(10, "Primitive copper smelting", "Copper", "1", "The ore is crushed using stone tools, and mixed with charcoal in a small furnace. The furnace is a one-use structure, built as a pit furnace, dug into the ground or a small clay furnace. Bellows or blowpipes must be used to reach temperatures of around 1,100°C required to melt the copper.  After hours of heating, small droplets of molten copper, known as copper prills, forms and settles at the bottom of the furnace. When the furnace is cooled, these copper prills can be collected.", "Copper Ore", "10", "Charcoal", "6", "Stone tools", "3", "0", "72", "", "", "", "Wood tools", "10", "Stone tools", "4", "Null", "0"),
    MAKING_PRIMITIVE_STONE_TOOLS(11, "Making primitive stone tools", "Stone tools", "1", "Using what can be found in the area, construct simple stone tools", "Null", "0", "Null", "0", "Null", "0", "0", "48", "", "", "", "Null", "0", "Null", "0", "Null", "0"),
    MAKING_COPPER_TOOLS_USING_STONE_TOOLS_AND_NO_MOLDS(12, "Making copper tools, using stone tools and no molds", "Copper tools", "1", "Copper prills or scraps are reheated and hammered. This process is called cold hammering or hot forging, and drives out any remaining impurities. This hardens the copper, making it suitable for tools. When the copper is sufficiently pure, it is again heated and hammered to form it into copper tools.", "Copper", "1", "Charcoal", "1", "Wood tools", "2", "", "72", "", "", "", "Stone tools", "3", "Wood tools", "10", "Null", "0"),
    MAKING_COPPER_TOOLS_USING_COPPER_TOOLS_AND_MOLDS_(13, "Making copper tools, using copper tools and molds ", "Copper tools", "1", "Copper prills or scraps are reheated and hammered. This process is called cold hammering or hot forging, and drives out any remaining impurities. This hardens the copper, making it suitable for tools. When the copper is sufficiently pure, it is heated in a furnace with a stone collection bowl. Using wooden tools, the molten copper is poored into molds. The cast copper tool is further refined by hammering, sharpening and polishing. ", "Copper", "1", "Charcoal", "1", "Null", "0", "", "48", "", "", "", "Copper tools", "10", "Stone tools", "10", "Null", "0"),
    PRIMITIVE_BLOOMERY_IRON_SMELTING_USING_STONE_TOOLS(14, "Primitive bloomery iron smelting, using stone tools", "Wrought Iron", "1", "The ore is broken into smaller pieces to increase the surface area, to improve smelting efficiency. This step is labor-intensive and requires hours of work with stone hammers. A bloomery furnace is built, able to create a partially solid mass of iron called a bloom, but not hot enough to fully melt the iron.  The temperature must reach just above 1,200°C to cause the iron reduction. The bloom is then hammered while still hot to consolidate the iron, expelling as much slag as possible. This step helps to compact the iron, reduce impurities, and give it a workable shape. The bloom contains a considerable amount of impurities even after initial hammering, so it must be reheated and hammered multiple times. This repetitive heating and hammering process gradually refines the iron, removing more slag and improving the metal’s consistency and workability.", "Iron Ore", "10", "Charcoal", "18", "Wood tools", "2", "", "200", "", "", "", "Stone tools", "16", "Wood tools", "12", "Null", "0"),
    BLOOMERY_IRON_SMELTING_USING_IRON_TOOLS(15, "Bloomery iron smelting, using iron tools", "Wrought Iron", "1", "The ore is broken into smaller pieces to increase the surface area, to improve smelting efficiency. This step is labor-intensive and requires hours of work with stone hammers. A bloomery furnace is built, able to create a partially solid mass of iron called a bloom, but not hot enough to fully melt the iron.  The temperature must reach just above 1,200°C to cause the iron reduction. The bloom is then hammered while still hot to consolidate the iron, expelling as much slag as possible. This step helps to compact the iron, reduce impurities, and give it a workable shape. The bloom contains a considerable amount of impurities even after initial hammering, so it must be reheated and hammered multiple times. This repetitive heating and hammering process gradually refines the iron, removing more slag and improving the metal’s consistency and workability.", "Iron Ore", "10", "Charcoal", "18", "Wood tools", "2", "", "120", "", "", "", "Iron tools", "8", "Stone tools", "12", "Wood tools", "12"),
    PRIMITIVE_IRON_SMITHING_USING_STONE_TOOLS_AND_BLOOMERY_FURNACE(16, "Primitive Iron Smithing, using stone tools and bloomery furnace", "Iron tools", "2", "Wrought iron is heated in a bloomery furnace, and extracted while still glowing hot. The blacksmith shapes it into the desired tool. If crafting an axe, for example, the blacksmith will flatten one end to form the blade and leave the opposite end thicker for attachment to a handle. Using different tools like hammers, chisels, and tongs, the blacksmith bends, cuts, and shapes the iron while it is hot and malleable. For more complex shapes, they might use a hardy (anvil tool) or specialized dies to achieve finer details. To improve durability, the blacksmith might use a technique known as work hardening, which involves hammering the edges of the tool to strengthen them. Alternatively, they may quench the tool, cooling it quickly in water or oil to harden its surface. This quenching process hardens the iron somewhat, though true tempering for steel is less applicable for pure wrought iron, which remains relatively soft compared to steel. After the tool has its final shape, the blacksmith uses grinding stones or abrasive materials to sharpen and polish the edges. This step enhances the tool's cutting ability for items like knives, chisels, or axes.", "Wrought Iron", "3", "Charcoal", "12", "Wood tools", "1", "", "100", "", "", "", "Stone tools", "12", "Wood tools", "12", "Null", "0"),
    PRIMITIVE_IRON_SMITHING_USING_IRON_TOOLS_AND_BLOOMERY_FURNACE(17, "Primitive Iron Smithing, using iron tools and bloomery furnace", "Iron tools", "3", "Wrought iron is heated in a bloomery furnace, and extracted while still glowing hot. The blacksmith shapes it into the desired tool. If crafting an axe, for example, the blacksmith will flatten one end to form the blade and leave the opposite end thicker for attachment to a handle. Using different tools like hammers, chisels, and tongs, the blacksmith bends, cuts, and shapes the iron while it is hot and malleable. For more complex shapes, they might use a hardy (anvil tool) or specialized dies to achieve finer details. To improve durability, the blacksmith might use a technique known as work hardening, which involves hammering the edges of the tool to strengthen them. Alternatively, they may quench the tool, cooling it quickly in water or oil to harden its surface. This quenching process hardens the iron somewhat, though true tempering for steel is less applicable for pure wrought iron, which remains relatively soft compared to steel. After the tool has its final shape, the blacksmith uses grinding stones or abrasive materials to sharpen and polish the edges. This step enhances the tool's cutting ability for items like knives, chisels, or axes.", "Wrought Iron", "4", "Charcoal", "14", "Wood tools", "1", "", "70", "", "", "", "Stone tools", "6", "Wood tools", "12", "Iron tools", "8"),
    BLOOMERY_CARBURIZATION(18, "Bloomery carburization", "Mild Steel", "2", "During  a bloomery heating, blacksmiths introduce carburization, a process in which iron is exposed to carbon-rich environments to create mild steel, a harder and more resilient material. This primitive form of steel is achieved by heating iron in charcoal for extended periods, allowing carbon to diffuse into the surface.", "Wrought Iron", "2", "Charcoal", "18", "Wood tools", "1", "", "60", "", "", "", "Stone tools", "6", "Wood tools", "12", "Iron tools", "2"),
    SHAFT_FURNACE_CRUCIBLE_MILD_STEEL_SMELTING(19, "Shaft Furnace Crucible Mild Steel Smelting", "Mild Steel", "150", "Using a Shaft Furnace, the temperature of the iron can be brought to 1,300–1,500°C, high enough to produce molten iron, especially with effective bellows. With this, it is much faster to produce steel in a crucible. ", "Wrought Iron", "160", "Charcoal", "240", "Null", "0", "", "3200", "", "", "", "Iron tools", "30", "Stone tools", "70", "Null", "0"),
    SHAFT_FURNACE_WROUGHT_IRON_SMELTING(20, "Shaft Furnace Wrought Iron Smelting", "Wrought Iron", "150", "Using a Shaft Furnace, the temperature of the iron can be brought to 1,300–1,500°C, high enough to produce molten iron, especially with effective bellows.  With this high temperature, the efficiency of the melting process increases, and requires fewer passes through the heating process to reduce impurities.", "Iron Ore", "1500", "Charcoal", "350", "Null", "0", "", "3200", "", "", "", "Iron tools", "20", "Stone tools", "70", "Null", "0"),
    SHAFT_FURNACE_IRON_CASTING_AND_SMITHING_OF_IRON_TOOLS(21, "Shaft Furnace Iron Casting and Smithing of Iron Tools", "Iron tools", "150", "Using a Shaft Furnace, the temperature of the iron can be brought to 1,300–1,500°C, high enough to produce molten iron, especially with effective bellows.  This allow production of cast iron, by pouring molten iron into molds for larger-scale production.", "Wrought Iron", "160", "Charcoal", "240", "Null", "0", "", "3200", "", "", "", "Iron tools", "40", "Stone tools", "80", "Null", "0"),
    SHAFT_FURNACE_CRUCIBLE_HARD_STEEL_SMELTING(22, "Shaft Furnace Crucible Hard Steel Smelting", "Hard Steel", "150", "Using a Shaft Furnace, the temperature of the iron can be brought to 1,300–1,500°C, high enough to produce molten iron, especially with effective bellows. TODO: More description here", "Wrought Iron", "160", "Charcoal", "240", "Null", "0", "", "3200", "", "", "", "Iron tools", "30", "Stone tools", "70", "Null", "0"),
    PRIMITIVE_CHARCOAL_BURNING(23, "Primitive Charcoal burning", StockKeepUnit.CHARCOAL, 50, "Description", StockKeepUnit.FIREWOOD_KG, 180, StockKeepUnit.NULL, 0, StockKeepUnit.NULL, 0, 0, 48, new SkillLevel(Skill.WOODWORKER, 3), null, null, null, 0, null, 0, null, 0),
    PRIMITIVE_STONE_TOOLS(24, "Primitive Stone Tools", StockKeepUnit.STONE_TOOLS, 1, "Making stone tools with only the tools and ingredients which can be found in nature.", StockKeepUnit.NULL, 0, StockKeepUnit.NULL, 0, StockKeepUnit.NULL, 0, 0, 80, new SkillLevel(Skill.STONEWORKER, 1), null, null, null, 0, null, 0, null, 0),
    PRIMITIVE_WOOD_TOOLS(25, "Primitive Wood Tools", StockKeepUnit.WOOD_TOOLS, 1, "Making wooden tools with only the tools which can be found in nature.", StockKeepUnit.WOOD_KG, 2, StockKeepUnit.NULL, 0, StockKeepUnit.NULL, 0, 0, 30, new SkillLevel(Skill.WOODWORKER, 1), null, null, null, 0, null, 0, null, 0);;
    private final ListSkuAndCount inputs = new ListSkuAndCount();
    private final ListSkuAndCount toolRequirements = new ListSkuAndCount();
    private final ListSkuAndCount outputs = new ListSkuAndCount();

    private final int recipeId;
    private final String recipeName;

    private final String processDescription;

    private final int calenderWaitDaysFromProductionToAvailable;
    private final int workTimeTimes10Minutes;
//    private final int skillLevel1;
//    private final int skillLevel2;


    public String getRecipeName() {
        return recipeName;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "inputs=" + inputs +
                ", outputs=" + outputs +
                ", recipeId=" + recipeId +
                ", recipeName='" + recipeName + '\'' +
                ", processDescription='" + processDescription + '\'' +
                ", calenderWaitTimeFromProductionToAvailable=" + calenderWaitDaysFromProductionToAvailable +
                ", workTimeTimes10Minutes=" + workTimeTimes10Minutes +
                '}';
    }

    Recipe(
            int recipeId,
            String recipeName,
            String productProduced,
            String producedAmount,
            String processDescription,
            String input_1,
            String input_1_amount,
            String input_2,
            String input_2_amount,
            String input_3,
            String input_3_amount,
            String calenderWaitTimeFromProductionToAvailable,
            String workTime_times10Minutes,
            String skillLevel_1,
            String skillLevel_2,
            String skillLevel_3,
            String Tool_Requirement_1,
            String Tool_Requirement_1_amount,
            String Tool_Requirement_2,
            String Tool_Requirement_2_amount,
            String Tool_Requirement_3,
            String Tool_Requirement_3_amount
    ) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;

        Optional<SkuAndCount> output1 = SkuAndCount.fromStrings(productProduced, producedAmount);
        output1.ifPresent(outputs::add);

        this.processDescription = processDescription;
        Optional<SkuAndCount> input1 = SkuAndCount.fromStrings(input_1, input_1_amount);
        input1.ifPresent(inputs::add);
        Optional<SkuAndCount> input2 = SkuAndCount.fromStrings(input_2, input_2_amount);
        input2.ifPresent(inputs::add);
        Optional<SkuAndCount> input3 = SkuAndCount.fromStrings(input_3, input_3_amount);
        input3.ifPresent(inputs::add);

        if (calenderWaitTimeFromProductionToAvailable.isEmpty()) {
            calenderWaitTimeFromProductionToAvailable = "0";
        }
        this.calenderWaitDaysFromProductionToAvailable = Integer.parseInt(calenderWaitTimeFromProductionToAvailable);
        workTimeTimes10Minutes = Integer.parseInt(workTime_times10Minutes);

//        skillLevel1 = Integer.parseInt(skillLevel_1);
//        skillLevel2 = Integer.parseInt(skillLevel_2);

        Optional<SkuAndCount> toolRequirement1 = SkuAndCount.fromStrings(Tool_Requirement_1, Tool_Requirement_1_amount);
        toolRequirement1.ifPresent(toolRequirements::add);
        Optional<SkuAndCount> toolRequirement2 = SkuAndCount.fromStrings(Tool_Requirement_2, Tool_Requirement_2_amount);
        toolRequirement2.ifPresent(toolRequirements::add);
        Optional<SkuAndCount> toolRequirement3 = SkuAndCount.fromStrings(Tool_Requirement_3, Tool_Requirement_3_amount);
        toolRequirement3.ifPresent(toolRequirements::add);
    }


    Recipe(
            int recipeId,
            String recipeName,
            StockKeepUnit productProduced,
            int producedAmount,
            String processDescription,
            StockKeepUnit input_1,
            int input_1_amount,
            StockKeepUnit input_2,
            int input_2_amount,
            StockKeepUnit input_3,
            int input_3_amount,
            int calenderWaitTimeFromProductionToAvailable,
            int workTime_times10Minutes,
            SkillLevel skillLevel_1,
            SkillLevel skillLevel_2,
            SkillLevel skillLevel_3,
            StockKeepUnit Tool_Requirement_1,
            int Tool_Requirement_1_amount,
            StockKeepUnit Tool_Requirement_2,
            int Tool_Requirement_2_amount,
            StockKeepUnit Tool_Requirement_3,
            int Tool_Requirement_3_amount
    ) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;


        Optional<SkuAndCount> output1 = SkuAndCount.from(productProduced, producedAmount);
        output1.ifPresent(outputs::add);

        this.processDescription = processDescription;
        Optional<SkuAndCount> input1 = SkuAndCount.from(input_1, input_1_amount);
        input1.ifPresent(inputs::add);
        Optional<SkuAndCount> input2 = SkuAndCount.from(input_2, input_2_amount);
        input2.ifPresent(inputs::add);
        Optional<SkuAndCount> input3 = SkuAndCount.from(input_3, input_3_amount);
        input3.ifPresent(inputs::add);

        this.calenderWaitDaysFromProductionToAvailable = calenderWaitTimeFromProductionToAvailable;
        workTimeTimes10Minutes = workTime_times10Minutes;

//        skillLevel1 = Integer.parseInt(skillLevel_1);
//        skillLevel2 = Integer.parseInt(skillLevel_2);

        Optional<SkuAndCount> toolRequirement1 = SkuAndCount.from(Tool_Requirement_1, Tool_Requirement_1_amount);
        toolRequirement1.ifPresent(inputs::add);
        Optional<SkuAndCount> toolRequirement2 = SkuAndCount.from(Tool_Requirement_2, Tool_Requirement_2_amount);
        toolRequirement2.ifPresent(inputs::add);
        Optional<SkuAndCount> toolRequirement3 = SkuAndCount.from(Tool_Requirement_3, Tool_Requirement_3_amount);
        toolRequirement3.ifPresent(inputs::add);
    }

    public int calculateProfitPrWorkTenMin(PriceList priceList) {
        Integer expenses = calculateRawProductPriceForProductionRun(priceList);
        Integer valueCreated = calculateValueOfFinishedProductForProductionRun(priceList);
        return (valueCreated - expenses) / workTimeTimes10Minutes;
    }

    public int calculateValueOfFinishedProductForProductionRun(PriceList priceList) {
        return outputs.stream().map(skuAndCount -> priceList.getPrice(skuAndCount.sku().getArrayId()) * skuAndCount.amount()).mapToInt(Integer::intValue).sum();
    }

    public int calculateRawProductPriceForProductionRun(PriceList priceList) {
        return inputs.stream().map(skuAndCount -> priceList.getPrice(skuAndCount.sku().getArrayId()) * skuAndCount.amount()).mapToInt(Integer::intValue).sum();
    }

    public ProductionImpactReport evaluateRawMaterialImpact(int workTimeAvailable, StockListing myRawMaterials) {
        int productionTimeLimit = workTimeAvailable / this.workTimeTimes10Minutes;
        OptionalInt rawMaterialsLimit = maxProductionRunsWithRawMaterials(myRawMaterials);

        int maxRuns = rawMaterialsLimit.isPresent() ? Math.min(rawMaterialsLimit.getAsInt(), productionTimeLimit) : productionTimeLimit;
        int leftOverTime = workTimeAvailable - maxRuns * workTimeTimes10Minutes;

        StockListing copyOfStock = myRawMaterials.createDuplicate();
        StockListing consumptionStock = StockListing.ofEmpty();
        for (SkuAndCount input : inputs.skuAndCounts) {
            consumptionStock.setSkuCount(input.sku().getArrayId(), input.amount() * maxRuns);
        }
        copyOfStock.retrieve(consumptionStock);

        return new ProductionImpactReport(maxRuns, leftOverTime, consumptionStock, copyOfStock);
    }

    public OptionalInt maxProductionRunsWithRawMaterials(StockListing myRawMaterials) {
        Stream<Integer> rawMaterialsLimits = inputs.stream().map(inputProduct -> howManyProductionRunsWillThisRawMaterialSupport(myRawMaterials, inputProduct));
        OptionalInt rawMaterialsLimit = rawMaterialsLimits.mapToInt(Integer::intValue).min();
        return rawMaterialsLimit;
    }

    private static int howManyProductionRunsWillThisRawMaterialSupport(StockListing myRawMaterials, SkuAndCount inputProduct) {
        return myRawMaterials.getSkuCount(inputProduct.sku().getArrayId()) / inputProduct.amount();
    }

    public void runProduction(int numberOfRuns, StockListing stock) {
        for (SkuAndCount input : inputs.skuAndCounts) {
            stock.addStockAmount(input.sku().getArrayId(), -input.amount() * numberOfRuns);
        }
        for (SkuAndCount output : outputs.skuAndCounts) {
            stock.addStockAmount(output.sku().getArrayId(), output.amount() * numberOfRuns);
        }
    }

    public int getWorkTimeTimes10Minutes() {
        return workTimeTimes10Minutes;
    }

    public int getCalenderWaitDaysFromProductionToAvailable() {
        return calenderWaitDaysFromProductionToAvailable;
    }

    public ListSkuAndCount getInputs() {return inputs; }
    public ListSkuAndCount getOutputs() {
        return outputs;
    }
    public ListSkuAndCount getToolRequirements() { return toolRequirements; }
}
