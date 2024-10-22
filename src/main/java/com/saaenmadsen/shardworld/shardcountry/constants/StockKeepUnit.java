package com.saaenmadsen.shardworld.shardcountry.constants;

public enum StockKeepUnit {
    SKU_SHUE (1, "shue", "clas1", "clas2");

    private int arrayId;
    private String productName;
    private String ClassificationLevel1;
    private String ClassificationLevel2;


    StockKeepUnit(int arrayId, String productName, String classificationLevel1, String classificationLevel2) {
        this.arrayId = arrayId;
        this.productName = productName;
        ClassificationLevel1 = classificationLevel1;
        ClassificationLevel2 = classificationLevel2;
    }

    public int getArrayId() {
        return arrayId;
    }

    public String getProductName() {
        return productName;
    }

    public String getClassificationLevel1() {
        return ClassificationLevel1;
    }

    public String getClassificationLevel2() {
        return ClassificationLevel2;
    }
}
