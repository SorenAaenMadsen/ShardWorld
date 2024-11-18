package com.saaenmadsen.shardworld.unittest.company;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.CompanyInformationBuilder;
import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.actors.company.direction.InnovateOurRecipesBoardMeeting;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettingsBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TacticalBoardMeetingTest {
    @Test
    public void testReduceNumberOfKnownrecipesToMax5() {
        CompanyInformation companyInformation = CompanyInformationBuilder.ofWorldDefault("Testcompany", WorldSettingsBuilder.ofDefault().build()).build();
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.PRIMITIVE_WOODEN_SHUE, 100));
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.GATHER_FIREWOOD, 100));
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.GATHER_CURED_WOOD, 90));
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.PRIMITIVE_TIMBER_CURING, 100));
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.WOOD_SAWING, 100));
        companyInformation.getKnownRecipes().add(new KnownRecipe(Recipe.GATHER_RAW_TIMBER, 100));
        CompanyDailyReport companyDailyReport = new CompanyDailyReport("Testcompany",1);
        InnovateOurRecipesBoardMeeting.reduceNumberOfKnownrecipesToMax5(companyInformation, companyDailyReport);

        assertThat("The recipe with worst projected revenue (GATHER_CURED_WOOD) should be the one removed.",
                companyInformation.getKnownRecipes().size(),
                equalTo(5));
//        boolean x = companyInformation.getKnownRecipes().stream().anyMatch(knownRecipe -> knownRecipe.getRecipe() == Recipe.GATHER_CURED_WOOD);
        assertThat("The recipe with worst projected revenue (GATHER_CURED_WOOD) should be the one removed.",
                companyInformation.getKnownRecipes().stream().anyMatch(knownRecipe -> knownRecipe.getRecipe()==Recipe.GATHER_CURED_WOOD),
                equalTo(false));
    }
}
