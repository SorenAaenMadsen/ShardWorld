package com.saaenmadsen.shardworld.actors.company;

import com.saaenmadsen.shardworld.actors.company.culture.CompanyCulture;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyType;
import com.saaenmadsen.shardworld.actors.company.flawor.CompanyFlawor;
import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.List;
import java.util.Random;

public class CompanyInformation {
    private final String companyId;
    private CompanyCulture culture;
    private CompanyFlawor companyFlawor;
    private StockListing warehouse;
    private List<KnownRecipe> myRecipes;
    private int workers = 10;
    private Random dice;
    private PriceList priceList;
    private MoneyBox moneyBox;
    private final CompanyType companyType;


    public CompanyInformation(String companyId, CompanyType companyType, CompanyCulture culture, CompanyFlawor companyFlawor, StockListing warehouse, List<KnownRecipe> myRecipes, int workers, Random dice, PriceList priceList, MoneyBox moneyBox) {
        this.companyId = companyId;
        this.companyType = companyType;
        this.culture = culture;
        this.companyFlawor = companyFlawor;
        this.warehouse = warehouse;
        this.myRecipes = myRecipes;
        this.workers = workers;
        this.dice = dice;
        this.priceList = priceList;
        this.moneyBox = moneyBox;
    }

    public int calculateWorkTimeAvailable() {
        return workers * 8;
    }

    public Random getDice() {
        return dice;
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

    public List<KnownRecipe> getKnownRecipes() {
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

    public boolean timeForTacticalBoardMeeting() {
        return (dice.nextInt(20) < 2);
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    public String getCompanyId() {
        return companyId;
    }

    public MoneyBox getMoneyBox() {
        return this.moneyBox;
    }
}
