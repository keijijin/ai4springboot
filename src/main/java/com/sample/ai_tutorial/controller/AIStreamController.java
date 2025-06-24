package com.sample.ai_tutorial.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.ai_tutorial.service.OpenAIService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/ai")
public class AIStreamController {

    private final OpenAIService openAIService;

    public AIStreamController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping(value = "/chat-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String prompt) {
        return openAIService.streamChatResponse(prompt);
    }
}