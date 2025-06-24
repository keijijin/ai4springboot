package com.sample.ai_tutorial.service;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.stereotype.Component;

import com.sample.ai_tutorial.tools.CalculatorTool;
import com.sample.ai_tutorial.tools.WeatherTool;

@Component
public class ReasoningAgent {

    private final ToolAgent agent;  // ToolAgent is the interface we defined

    public ReasoningAgent(CalculatorTool calculatorTool, WeatherTool weatherTool) {
        // Initialize the OpenAI chat model (GPT-3.5-Turbo in this example)
        OpenAiChatModel llm = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))  // or use your config
                .modelName("gpt-3.5-turbo")               // can use OpenAiChatModelName enum
                .temperature(0.0)  // using 0 for more deterministic tool selection:contentReference[oaicite:10]{index=10}
                .build();

        // (Optional) Configure memory to allow the agent to remember tool context
        ChatMemory memory = MessageWindowChatMemory.withMaxMessages(5);
        // At least 3 messages are recommended for tool use:contentReference[oaicite:11]{index=11}:
        // (e.g., user query, tool output, and final answer)

        // Build the AI agent service with the tools
        this.agent = AiServices.builder(ToolAgent.class)
                     .chatModel(llm)
                     .chatMemory(memory)
                     .tools(calculatorTool, weatherTool)  // provide both tool instances
                     .build();
    }

    public String ask(String input) {
        // Delegate to the AI service's implementation
        return agent.ask(input);
    }
}
