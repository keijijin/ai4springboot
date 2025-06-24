package com.sample.ai_tutorial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sample.ai_tutorial.service.ReasoningAgent;

import java.util.Map;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final ReasoningAgent reasoningAgent;

    public AgentController(ReasoningAgent reasoningAgent) {
        this.reasoningAgent = reasoningAgent;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askAgent(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        String answer = reasoningAgent.ask(prompt);
        return ResponseEntity.ok(answer);
    }
}
