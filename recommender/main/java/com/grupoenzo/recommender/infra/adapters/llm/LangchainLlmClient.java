package com.grupoenzo.recommender.infra.adapters.llm;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import com.grupoenzo.recommender.core.ports.llm.LlmClient;

public class LangchainLlmClient implements LlmClient {
    private final ChatLanguageModel model;

    public LangchainLlmClient(String apiKey) {
        this.model = OpenAiChatModel.builder()
            .apiKey(apiKey)
            .modelName("gpt-4-turbo")
            .temperature(0.7)
            .build();
    }

    @Override
    public String generate(String prompt) {
        return model.generate(prompt);
    }
}