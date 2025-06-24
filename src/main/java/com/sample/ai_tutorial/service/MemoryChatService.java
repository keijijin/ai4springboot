package com.sample.ai_tutorial.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemoryChatService {

    private final Assistant assistant;

    public MemoryChatService(@Value("${openai.api-key}") String apiKey) {
        ChatModel model = OpenAiChatModel.builder()
            .apiKey(apiKey)
            .modelName("gpt-3.5-turbo")  // または "gpt-4" など、必要に応じて変更
            .temperature(0.7)
            .build();

        ChatMemory memory = MessageWindowChatMemory.withMaxMessages(10);

        this.assistant = AiServices.builder(Assistant.class)
            .chatMemory(memory)
            .chatModel(model)  // または .chatModel(model)
            .build();
    }

    public String chat(String userInput) {
        return assistant.chat(userInput);
    }

    // LangChain4J 1.1.0ではインターフェースが推奨される
    public interface Assistant {
        String chat(String userMessage);
    }
}
