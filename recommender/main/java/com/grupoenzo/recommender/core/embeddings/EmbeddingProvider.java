package com.grupoenzo.recommender.core.embeddings;

public interface EmbeddingProvider {
    float[] embed(String text);
}
