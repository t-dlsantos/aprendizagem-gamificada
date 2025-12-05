package com.grupoenzo.recommender.core.ports.llm;

public interface LlmClient {
    String generate(String prompt);   
}
