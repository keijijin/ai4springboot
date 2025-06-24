package com.sample.ai_tutorial.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.ai_tutorial.service.OpenAIService;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final OpenAIService openAIService;

    public AIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/summarize")
    public ResponseEntity<String> summarize(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String summary = openAIService.summarizeText(text);
        return ResponseEntity.ok(summary);
    }
}