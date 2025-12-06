package com.grupoenzo.recommender.infra.llm;

import com.grupoenzo.recommender.core.ports.llm.LlmClient;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class OpenAiLlmClient implements LlmClient {

    private final ChatLanguageModel chatModel;

    public OpenAiLlmClient(@Value("${langchain4j.open-ai.chat-model.api-key}") String apiKey) {
        this.chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-3.5-turbo")
                .build();
    }

    @Override
    public String generate(String prompt) {
        return chatModel.generate(prompt);
    }
}
