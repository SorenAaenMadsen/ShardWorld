package com.saaenmadsen.shardworld.unittest.company;

import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.companies.Company;
import com.saaenmadsen.shardworld.constants.companies.culture.CompanyCulture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyCultureTest {
    @Test
    public void testStringsForFun() {
        Company company = new Company(Recipe.GATHER_CURED_WOOD, Recipe.GATHER_FIREWOOD);
        System.out.println(company);
    }
}
