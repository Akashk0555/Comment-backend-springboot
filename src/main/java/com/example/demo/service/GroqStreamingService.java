package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class GroqStreamingService {

    private final WebClient webClient;

    public GroqStreamingService(@Value("${groq.api.key}") String apiKey,
                                @Value("${groq.api.url}") String apiUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public Flux<String> streamGroq(String prompt) {
        String body = """
            {
              "model": "llama3-70b-8192",
              "messages": [
                {"role": "system", "content": "You are a helpful assistant."},
                {"role": "user", "content": "%s"}
              ],
              "stream": true
            }
            """.formatted(prompt);

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                // Clean tokens: Groq streams JSON chunks, we extract "delta.content"
                .map(this::extractDeltaContent)
                .filter(s -> !s.isBlank());
    }

    private String extractDeltaContent(String chunk) {
        try {
            if (chunk.contains("\"content\"")) {
                int start = chunk.indexOf("\"content\":\"") + 11;
                int end = chunk.indexOf("\"", start);
                if (start > 0 && end > start) {
                    return chunk.substring(start, end);
                }
            }
        } catch (Exception ignored) {}
        return "";
    }
}