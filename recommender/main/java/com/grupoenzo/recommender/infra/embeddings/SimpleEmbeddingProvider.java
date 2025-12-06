package com.grupoenzo.recommender.infra.embeddings;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.grupoenzo.recommender.core.embeddings.EmbeddingProvider;

@Component
@Profile("test")
public class SimpleEmbeddingProvider implements EmbeddingProvider {
    @Override
    public float[] embed(String text) {
        // Implementação simples: hash do texto em um embedding
        if (text == null || text.isEmpty()) {
            return new float[384]; // dimensão padrão
        }
        
        float[] embedding = new float[384];
        int hash = text.hashCode();
        
        for (int i = 0; i < embedding.length; i++) {
            embedding[i] = (float) Math.sin(hash + i) / (i + 1);
        }
        
        return embedding;
    }
}
