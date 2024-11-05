package com.saaenmadsen.shardworld.actors.company;

import com.saaenmadsen.shardworld.actors.company.culture.CompanyCulture;
import com.saaenmadsen.shardworld.actors.company.flawor.CompanyFlawor;
import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.ArrayList;
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


    public CompanyInformation(String companyId) {
        this.companyId = companyId;
        this.culture = new CompanyCulture();
        this.warehouse = StockListing.createEmptyStockListing();
        this.dice = new Random();
        this.myRecipes = new ArrayList<>();
        this.priceList = new PriceList();
        this.companyFlawor = new CompanyFlawor();
        this.moneyBox = new MoneyBox();
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
        return (dice.nextInt(20)<2);
    }

    public boolean timeForStrategicBoardMeeting() {
        return (dice.nextInt(180)<2);
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
