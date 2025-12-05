package com.grupoenzo.recommender.infra.vector;

import java.util.List;
import java.util.Map;

public interface VectorStore {
    void upsert(String id, float[] vector, Map<String, String> metadata);
    List<String> search(String query, int topK);
}
