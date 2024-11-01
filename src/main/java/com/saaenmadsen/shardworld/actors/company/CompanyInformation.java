package com.saaenmadsen.shardworld.actors.company;

import com.saaenmadsen.shardworld.actors.company.culture.CompanyCulture;
import com.saaenmadsen.shardworld.actors.company.flawor.CompanyFlawor;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.ArrayList;
import java.util.List;

public class CompanyInformation {
    private CompanyCulture culture;
    private CompanyFlawor companyFlawor;
    private StockListing warehouse;
    private List<KnownRecipe> myRecipes = new ArrayList<>();
    private int workers = 10;

    public CompanyInformation() {
        this.warehouse = StockListing.createEmptyStockListing();
    }

    public int calculateWorkTimeAvailable() {
        return workers * 8;
    }














    public CompanyCulture getCulture() {
        return culture;
    }

    public void setCulture(CompanyCulture culture) {
        this.culture = culture;
    }

    public CompanyFlawor getCompanyFlawor() {
        return companyFlawor;
    }

    public void setCompanyFlawor(CompanyFlawor companyFlawor) {
        this.companyFlawor = companyFlawor;
    }

    public StockListing getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StockListing warehouse) {
        this.warehouse = warehouse;
    }

    public List<KnownRecipe> getMyRecipes() {
        return myRecipes;
    }

    public void setMyRecipes(List<KnownRecipe> myRecipes) {
        this.myRecipes = myRecipes;
    }

    public int getWorkers() {
        return workers;
    }

    public void setWorkers(int workers) {
        this.workers = workers;
    }
}
