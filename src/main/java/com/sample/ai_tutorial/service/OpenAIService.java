package com.sample.ai_tutorial.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

@Service
public class OpenAIService {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public String summarizeText(String input) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> message = Map.of(
                "role", "user",
                "content", "Summarize this text: " + input);

        Map<String, Object> request = Map.of(
                "model", model,
                "messages", List.of(message));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(OPENAI_API_URL, entity, Map.class);

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        Map<String, Object> messageData = (Map<String, Object>) choices.get(0).get("message");

        return (String) messageData.get("content");
    }

    public Flux<String> streamChatResponse(String userPrompt) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();

        Map<String, Object> message = Map.of(
                "role", "user",
                "content", userPrompt);

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", List.of(message),
                "stream", true);

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(String.class) // Handle as Server-Sent Events
                .flatMap(response -> {
                    // parse out the delta content
                    if (response.startsWith("data: ")) {
                        response = response.substring(6);
                    }
                    if (response.trim().equals("[DONE]"))
                        return Flux.empty();

                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode json = mapper.readTree(response);
                        return Flux.just(json.at("/choices/0/delta/content").asText());
                    } catch (Exception e) {
                        return Flux.empty();
                    }
                });
    }
}