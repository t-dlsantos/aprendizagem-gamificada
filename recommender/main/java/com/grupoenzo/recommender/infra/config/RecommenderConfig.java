package com.grupoenzo.recommender.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.grupoenzo.recommender.core.embeddings.EmbeddingProvider;
import com.grupoenzo.recommender.core.ports.repositories.CourseRepository;
import com.grupoenzo.recommender.core.ports.repositories.EnrollmentRepository;
import com.grupoenzo.recommender.core.ports.llm.LlmClient;
import com.grupoenzo.recommender.infra.vector.VectorStore;
import com.grupoenzo.recommender.infra.vector.InMemoryVectorStore;
import com.grupoenzo.recommender.core.usecases.GenerateRank;
import com.grupoenzo.recommender.core.usecases.RankExplain;
import com.grupoenzo.recommender.infra.llm.DummyLlmClient;
import java.util.Map;

@Configuration
public class RecommenderConfig {

    @Bean
    public VectorStore vectorStore(EmbeddingProvider embProvider, CourseRepository courseRepo) {
        InMemoryVectorStore store = new InMemoryVectorStore();
        // indexa cursos (se houver)
        var courses = courseRepo.findAll();
        if (courses != null && !courses.isEmpty()) {
            courses.forEach(c -> {
                float[] e = embProvider.embed(c.getShortDescription());
                store.upsert(c.getId().toString(), e, Map.of("title", c.getTitle()));
            });
        }
        return store;
    }

    @Bean
    public GenerateRank generateRank(CourseRepository repo, EnrollmentRepository er, EmbeddingProvider emb) {
        return new GenerateRank(repo, er, emb);
    }

    @Bean
    public RankExplain rankExplain(LlmClient llm, EmbeddingProvider emb, VectorStore vs) {
        return new RankExplain(llm, emb, vs);
    }
}
