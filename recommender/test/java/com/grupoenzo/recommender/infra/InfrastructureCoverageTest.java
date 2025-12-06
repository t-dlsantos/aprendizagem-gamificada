package com.grupoenzo.recommender.infra;

import com.grupoenzo.recommender.infra.embeddings.SimpleEmbeddingProvider;
import com.grupoenzo.recommender.infra.llm.DummyLlmClient;
import com.grupoenzo.recommender.infra.vector.InMemoryVectorStore;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InfrastructureCoverageTest {

    @Test
    void testSimpleEmbeddingProvider() {
        SimpleEmbeddingProvider provider = new SimpleEmbeddingProvider();
        float[] embedding = provider.embed("test");
        assertNotNull(embedding);
        assertEquals(384, embedding.length);

        float[] emptyEmbedding = provider.embed("");
        assertNotNull(emptyEmbedding);
        assertEquals(384, emptyEmbedding.length);
        
        float[] nullEmbedding = provider.embed(null);
        assertNotNull(nullEmbedding);
        assertEquals(384, nullEmbedding.length);
    }

    @Test
    void testDummyLlmClient() {
        DummyLlmClient client = new DummyLlmClient();
        String result = client.generate("prompt");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testInMemoryVectorStore() {
        InMemoryVectorStore store = new InMemoryVectorStore();
        float[] vector = new float[]{1.0f, 0.0f};
        Map<String, String> meta = new HashMap<>();
        meta.put("title", "Test Title");
        
        store.upsert("1", vector, meta);
        
        List<String> results = store.search("query", 1);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Test Title", results.get(0));

        // Test empty title filtering
        store.upsert("2", vector, Map.of("title", ""));
        List<String> results2 = store.search("query", 10);
        assertEquals(1, results2.size()); // Should still be 1 (Test Title)
    }
}
