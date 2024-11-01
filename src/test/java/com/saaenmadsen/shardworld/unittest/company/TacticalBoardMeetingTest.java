package com.saaenmadsen.shardworld.unittest.company;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.DailyReport;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.actors.company.direction.TacticalBoardMeeting;
import com.saaenmadsen.shardworld.constants.Recipe;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TacticalBoardMeetingTest {
    @Test
    public void testReduceNumberOfKnownRecipiesToMax5() {
        CompanyInformation companyInformation = new CompanyInformation("Testcompany");
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.PRIMITIVE_WOODEN_SHUE, 100));
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.GATHER_FIREWOOD, 100));
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.GATHER_CURED_WOOD, 90));
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.PRIMITIVE_TIMBER_CURING, 100));
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.WOOD_SAWING, 100));
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.GATHER_RAW_TIMBER, 100));
        DailyReport dailyReport = new DailyReport();
        TacticalBoardMeeting.reduceNumberOfKnownRecipiesToMax5(companyInformation, dailyReport);

        assertThat("The recipe with worst projected revenue (GATHER_CURED_WOOD) should be the one removed.",
                companyInformation.getKnownRecipes().size(),
                equalTo(5));
//        boolean x = companyInformation.getKnownRecipes().stream().anyMatch(knownRecipe -> knownRecipe.getRecipe() == Recipe.GATHER_CURED_WOOD);
        assertThat("The recipe with worst projected revenue (GATHER_CURED_WOOD) should be the one removed.",
                companyInformation.getKnownRecipes().stream().anyMatch(knownRecipe -> knownRecipe.getRecipe()==Recipe.GATHER_CURED_WOOD),
                equalTo(false));
    }
}