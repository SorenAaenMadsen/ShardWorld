package com.saaenmadsen.shardworld.aitools;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class AiTextGenerator {
    private static final String LOCALHOST = "http://localhost:11434";
    private static final String modelName = "gemma2:2b";

    public static ChatLanguageModel connectModel() {
        return OllamaChatModel.builder()
                .baseUrl(LOCALHOST)
                .modelName(modelName)
                .build();
    }
}
