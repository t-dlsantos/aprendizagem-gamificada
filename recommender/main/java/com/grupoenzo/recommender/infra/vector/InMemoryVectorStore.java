package com.grupoenzo.recommender.infra.vector;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryVectorStore implements VectorStore {
    private final Map<String, float[]> vectors = new HashMap<>();
    private final Map<String, Map<String,String>> metadata = new HashMap<>();

    @Override
    public void upsert(String id, float[] vector, Map<String, String> meta) {
        vectors.put(id, vector);
        metadata.put(id, meta);
    }

    @Override
    public List<String> search(String query, int topK) {
        // stub: return topK titles from metadata
        return metadata.values().stream()
            .map(m -> m.getOrDefault("title", ""))
            .filter(s -> !s.isEmpty())
            .limit(topK)
            .collect(Collectors.toList());
    }
}
