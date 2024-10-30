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
//    private static final String LOCALHOST = "http://localhost:3000";
    private static final String LOCALHOST = "http://localhost:11434";
    private static final String LOCALHOST_GENERATE_API_URL = "http://localhost:3000/api/v1/generate";

    //"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImJmMzAxMDFiLTdjOWYtNGZmOC05NjY0LTMyYzgwZjZjMDcwZSJ9.eA0PMYak2DoOg1_RuiWo0nwB-M-iik9BHKnnRUUz4ho"
    // Start docker with only API open:
    // docker run -d -p 3000:8080 -e =your_secret_key -v open-webui:/app/backend/data --name open-webui --restart always ghcr.io/open-webui/open-webui:main

    // Start docker with this command:
    // docker run -d -p 3000:8080 --add-host=host.docker.internal:host-gateway -e OPENAI_API_KEYS="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImJmMzAxMDFiLTdjOWYtNGZmOC05NjY0LTMyYzgwZjZjMDcwZSJ9.eA0PMYak2DoOg1_RuiWo0nwB-M-iik9BHKnnRUUz4ho" -v open-webui:/app/backend/data --name open-webui --restart always ghcr.io/open-webui/open-webui:main

    // docker run
    //      -d
    //      -p 3000:8080
    //      --add-host=host.docker.internal:host-gateway
    //      -v open-webui:/app/backend/data
    //      --name open-webui
    //      --restart always
    //      ghcr.io/open-webui/open-webui:main


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
    public void simpleTestFromChatGpt(){
        OllamaChatModel chatLanguageModel = OllamaChatModel.builder()
                .baseUrl(LOCALHOST)
                .modelName("gemma2:2b") // Replace with the specific model name if necessary
                .build();
        // Define the input question
        String question = "What are the benefits of using Kotlin over Java?";

        // Send the question to the language model and receive the response
        String response = chatLanguageModel.generate(question);

        // Assert that the response is not null or empty
        assertNotNull(response, "The response should not be null");
        assertFalse(response.isEmpty(), "The response should not be empty");

        // Optionally print the response for inspection
        System.out.printf("Response: %s%n", response);
    }

    @Test
    public void testModelResponse() throws Exception {
        // Define the question prompt
        String prompt = "What are the benefits of using Kotlin over Java?";

        // Construct the JSON request body
        String requestBody = String.format("{\"model\": \"gemma2:2b\", \"prompt\": \"%s\"}", prompt);

        // Create an HTTP POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_GENERATE_API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send the request and get the response
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check response status and content
        assertNotNull(response.body(), "The response should not be null");
        assertFalse(response.body().isEmpty(), "The response should not be empty");

        // Print the response for inspection
        System.out.printf("Response: %s%n", response.body());
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

    @Test
    public void simpleTest2() throws Exception {
        String modelName = "gemma2:2b";
        String prompt = "Name 4 animals";

        String response = sendRequest(modelName, prompt);
        System.out.printf("Response: %s%n", response);
    }

    private static String sendRequest(String modelName, String prompt) throws Exception {
        String requestBody = String.format("{\"model\": \"%s\", \"prompt\": \"%s\"}", modelName, prompt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_GENERATE_API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }


    private static ChatLanguageModel connectModel(String modelName) {
        return OllamaChatModel.builder()
                .baseUrl(LOCALHOST)
                .modelName(modelName)
                .build();
    }
}
