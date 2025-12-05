package com.grupoenzo.recommender.infra.config;

@Configuration
public class RecommenderConfig {

    @Bean
    public VectorStore vectorStore(EmbeddingProvider embProvider, CourseRepository courseRepo) {
        InMemoryVectorStore store = new InMemoryVectorStore();
        // indexa cursos
        courseRepo.findAllCourses().forEach(c -> {
            float[] e = embProvider.embed(c.getShortDescription());
            store.upsert(c.getId().toString(), e, Map.of("title", c.getTitle()));
        });
        return store;
    }

    @Bean
    public GenerateCandidatesUseCase candidateGenerator(CourseRepository repo, EnrollmentRepository er) {
        return new GenerateCandidatesUseCase(repo, er);
    }

    @Bean
    public RerankAndExplainUseCase reranker(LlmClient llm, EmbeddingProvider emb, VectorStore vs) {
        return new RerankAndExplainUseCase(llm, emb, vs);
    }
}
