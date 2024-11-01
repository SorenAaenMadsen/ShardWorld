package com.saaenmadsen.shardworld.unittest.company;

import com.saaenmadsen.shardworld.actors.company.flawor.CompanyFlawor;
import com.saaenmadsen.shardworld.constants.Recipe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyCultureTest {
    @Test
    public void testStringsForFun() {
        CompanyFlawor company = new CompanyFlawor(Recipe.GATHER_CURED_WOOD, Recipe.GATHER_FIREWOOD);
        System.out.println(company);
    }
}
