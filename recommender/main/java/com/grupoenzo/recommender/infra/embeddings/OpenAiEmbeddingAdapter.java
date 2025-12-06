package com.grupoenzo.recommender.infra.embeddings;

import com.grupoenzo.recommender.core.embeddings.EmbeddingProvider;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class OpenAiEmbeddingAdapter implements EmbeddingProvider {

    private final EmbeddingModel embeddingModel;

    public OpenAiEmbeddingAdapter(@Value("${langchain4j.open-ai.chat-model.api-key}") String apiKey) {
        // Usando text-embedding-3-small que é mais barato e eficiente
        this.embeddingModel = OpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("text-embedding-3-small")
                .build();
    }

    @Override
    public float[] embed(String text) {
        return embeddingModel.embed(text).content().vector();
    }
}
