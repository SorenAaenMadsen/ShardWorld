package com.saaenmadsen.shardworld.unittest.ollamaai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.junit.jupiter.api.Test;

public class UseAiToGenerateData {

    private static final String LOCALHOST = "http://localhost:11434";
    private static final String modelName = "gemma2:2b";

    @Test
    public void generateCompanyName() {
        OllamaChatModel.builder()
                .baseUrl(LOCALHOST)
                .modelName(modelName)
                .build();
        ChatLanguageModel model = OllamaChatModel.builder().baseUrl(LOCALHOST).modelName(modelName).build();

        String answer = model.generate("Generate name of a shue producing company");
        System.out.println(answer);
    }
}
