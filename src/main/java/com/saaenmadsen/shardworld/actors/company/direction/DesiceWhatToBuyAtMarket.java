package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import com.saaenmadsen.shardworld.recipechoice.ProductionImpactReport;

import java.util.ArrayList;
import java.util.Optional;

public class DesiceWhatToBuyAtMarket {

    private StockListing shoppingList;
    private final CompanyInformation companyInformation;
    private final CompanyDailyReport companyDailyReport;

    public DesiceWhatToBuyAtMarket(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        this.companyInformation = companyInformation;
        this.companyDailyReport = companyDailyReport;
        this.shoppingList = StockListing.ofEmpty();
    }

    public StockListing decide() {
        addRawMaterialsForTwoMostProfitableRecipes(companyInformation);
        return shoppingList;
    }

    public void addRawMaterialsForTwoMostProfitableRecipes(CompanyInformation companyInformation) {
        int workTimeAvailable = this.companyInformation.calculateWorkTimeAvailable();
        ArrayList<KnownRecipe> mostProfitableRecipes = getListOfTwoMostProfitableRecipes(this.companyInformation.getPriceList());
        for (KnownRecipe knownRecipe : mostProfitableRecipes) {
            ProductionImpactReport evaluation = knownRecipe.getRecipe().evaluateRawMaterialImpact(workTimeAvailable, StockListing.createMaxedOutStockListing());
            shoppingList.addStockFromList(evaluation.usedRawMaterial());
        }
    }

    private ArrayList<KnownRecipe> getListOfTwoMostProfitableRecipes(PriceList priceList) {
        Optional<KnownRecipe> prepare1 = Optional.empty();
        Optional<KnownRecipe> prepare2 = Optional.empty();

        for (KnownRecipe myRecipe : companyInformation.getKnownRecipes()) {
            myRecipe.setProfitability(myRecipe.recipe().calculateProfitPrWorkTenMin(priceList));
            if (prepare1.isEmpty()) {
                prepare1 = Optional.of(myRecipe);
            } else {
                if (prepare2.isEmpty()) {
                    prepare2 = Optional.of(myRecipe);
                } else {
                    if (prepare1.get().getProfitability() < myRecipe.getProfitability()) {
                        prepare1 = Optional.of(myRecipe);
                    } else {
                        if (prepare2.get().getProfitability() < myRecipe.getProfitability()) {
                            prepare2 = Optional.of(myRecipe);
                        }
                    }
                }
            }
        }
        ArrayList<KnownRecipe> result = new ArrayList<>();
        if (prepare1.isPresent()) {
            result.add(prepare1.get());
        }
        if (prepare2.isPresent()) {
            result.add(prepare2.get());
        }
        return result;
    }
}
