package com.saaenmadsen.shardworld.unittest.ollamaai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
//import org.testcontainers.ollama.OllamaContainer;
//import org.testcontainers.utility.DockerImageName;

public class OllamaAiConnectTest {
    private static final String LOCALHOST = "http://localhost:11434";

    @Test
    public void simpleTestFromDocsLangChain(){
        // The model name to use (e.g., "orca-mini", "mistral", "llama2", "codellama", "phi", or
        // "tinyllama")
        String modelName = "gemma2:2b";

        // Create and start the Ollama container
//        OllamaContainer ollama =
//                new OllamaContainer(DockerImageName.parse("langchain4j/ollama-" + modelName + ":latest")
//                        .asCompatibleSubstituteFor("ollama/ollama"));
//        ollama.start();

        // Build the ChatLanguageModel
        ChatLanguageModel model =
                OllamaChatModel.builder().baseUrl(LOCALHOST).modelName(modelName).build();

        // Example usage

        String answer = model.generate("Provide 3 short bullet points explaining why Java is awesome");
        System.out.println(answer);

        // Stop the Ollama container
//        ollama.stop();
    }


    @Test
    public void simpleTest() {
        var model = connectModel("gemma2:2b");
        String response = model.generate("Name 4 animals");
        System.out.printf("Response: %s%n", response);
//        Object textCompletionChain = TextCompletionChain.builder()
//                .languageModel(ollamaClient)
//                .build();
    }



    private static ChatLanguageModel connectModel(String modelName) {
        return OllamaChatModel.builder()
                .baseUrl(LOCALHOST)
                .modelName(modelName)
                .build();
    }
}
