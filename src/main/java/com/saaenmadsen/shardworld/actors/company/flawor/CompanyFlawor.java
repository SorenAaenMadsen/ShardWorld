package com.saaenmadsen.shardworld.actors.company.flawor;

import com.saaenmadsen.shardworld.actors.company.culture.CompanyCulture;
import com.saaenmadsen.shardworld.aitools.AiTextGenerator;
import com.saaenmadsen.shardworld.constants.Recipe;
import dev.langchain4j.model.chat.ChatLanguageModel;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CompanyFlawor {
    private String companyName;
    private String companyMarketingMotto;
    private String leadershipStyle;

    private CompanyCulture culture;
    private Recipe[] knownRecipes;



    public CompanyFlawor(Recipe... knownRecipes) {
        this.knownRecipes = knownRecipes;
        culture = new CompanyCulture();
        generateAiCompanyDescription();
    }

    private void generateAiCompanyDescription(){
        ChatLanguageModel chatLanguageModel = AiTextGenerator.connectModel();

        String productionProcesses = Arrays.stream(knownRecipes).map(knownRecipes -> knownRecipes.getProcessDescription()).collect(Collectors.joining(", "));

        String companyDescription = "a company producing by " + productionProcesses + "";


        this.companyName = chatLanguageModel.generate("Choose a random good enough company name for " + companyDescription + " and make it a short answer with just one name.").trim();
        this.companyMarketingMotto = chatLanguageModel.generate("Make a company marketing slogan for "+ companyDescription +", using max 120 characters. Return just that marketing motto as a plain string and nothing else.").trim();
        this.leadershipStyle = chatLanguageModel.generate("Describe the leadership style of "+ companyDescription +" in 200 characters, with the added knowledge that it is " + culture.getInnovativenessLevel().getDescription() + ". Return just the leadership style description as a string.").trim();

    }

    @Override
    public String toString() {
        return "Company{" +
                "\n   companyName='" + companyName + '\'' +
                ", \n   companyMarketingMotto='" + companyMarketingMotto + '\'' +
                ", \n   leadershipStyle='" + leadershipStyle + '\'' +
                ", \n   culture=" + culture +
                ", \n   knownRecipes=" + Arrays.toString(knownRecipes) +
                "\n}";
    }
}
