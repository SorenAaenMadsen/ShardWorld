package com.saaenmadsen.shardworld.constants.companies;

import com.saaenmadsen.shardworld.aitools.AiTextGenerator;
import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.companies.culture.CompanyCulture;
import dev.langchain4j.model.chat.ChatLanguageModel;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Company {
    private String companyName;
    private String companyMarketingMotto;
    private String leadershipStyle;

    private CompanyCulture culture;
    private Recipe[] knownRecipes;



    public Company(Recipe... knownRecipes) {
        this.knownRecipes = knownRecipes;
        culture = new CompanyCulture();
        generateAiCompanyDescription();
    }

    private void generateAiCompanyDescription(){
        ChatLanguageModel chatLanguageModel = AiTextGenerator.connectModel();

        String productionProcesses = Arrays.stream(knownRecipes).map(knownRecipes -> knownRecipes.getProcessDescription()).collect(Collectors.joining(", "));

        String request = "Choose a random good enough company name for a company producing by " + productionProcesses + " and make it a short answer with just one name.";


        this.companyName = chatLanguageModel.generate(request).trim();
        this.companyMarketingMotto = chatLanguageModel.generate("Make a company marketing slogan for that company, using max 120 characters. Return just that marketing motto as a plain string and nothing else.").trim();
        this.leadershipStyle = chatLanguageModel.generate("Describe the leadership style of that company in 200 characters, with the added knowledge that it is " + culture.getInnovativenessLevel().getDescription() + ". Return just the leadership style description as a string.").trim();

    }

    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", companyMarketingMotto='" + companyMarketingMotto + '\'' +
                ", leadershipStyle='" + leadershipStyle + '\'' +
                ", culture=" + culture +
                ", knownRecipes=" + Arrays.toString(knownRecipes) +
                '}';
    }
}
