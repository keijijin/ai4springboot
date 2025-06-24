package com.sample.ai_tutorial.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.ai_tutorial.service.MemoryChatService;

@RestController
@RequestMapping("/api/langchain")
public class LangChainController {

    private final MemoryChatService memoryChatService;

    public LangChainController(MemoryChatService memoryChatService) {
        this.memoryChatService = memoryChatService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String prompt) {
        return memoryChatService.chat(prompt);
    }
}