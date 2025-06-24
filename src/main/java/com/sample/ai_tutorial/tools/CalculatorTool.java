package com.sample.ai_tutorial.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTool {

    @Tool
    public String calculatePercentage(String input) {
        System.out.println("✅ CalculatorTool invoked with input: " + input);
        try {
            String[] parts = input.split(" of ");
            double percent = Double.parseDouble(parts[0].replace("%", "").trim());
            double value = Double.parseDouble(parts[1].trim());
            double result = (percent / 100) * value;
            return String.format("%.2f", result);
        } catch (Exception e) {
            return "Invalid input for percentage calculation.";
        }
    }
}
