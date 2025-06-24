package com.sample.ai_tutorial.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class WeatherTool {

    @Tool
    public String getCurrentWeather(String city) {
        System.out.println("✅ WeatherToll invoked with input: " + city);
        // In real-world, call an API here like OpenWeather
        return "It is sunny in " + city;
    }
}