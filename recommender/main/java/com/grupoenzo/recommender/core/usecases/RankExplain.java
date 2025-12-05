package com.grupoenzo.recommender.core.usecases;

import com.grupoenzo.recommender.core.ports.llm.LlmClient;
import com.grupoenzo.recommender.core.embeddings.EmbeddingProvider;
import com.grupoenzo.recommender.infra.vector.VectorStore;
import com.grupoenzo.recommender.core.domain.StudentProfile;
import com.grupoenzo.recommender.core.domain.RecommendedCourse;
import com.grupoenzo.recommender.core.domain.ExplainedRecommendation;
import java.util.List;
import java.util.stream.Collectors;

public class RankExplain {
    private final LlmClient llm;
    private final EmbeddingProvider embeddingProvider;
    private final VectorStore vectorStore;

    public RankExplain(LlmClient llm, EmbeddingProvider embeddingProvider, VectorStore vectorStore) {
        this.llm = llm;
        this.embeddingProvider = embeddingProvider;
        this.vectorStore = vectorStore;
    }

    public List<ExplainedRecommendation> execute(StudentProfile profile, List<RecommendedCourse> candidates) {
        return candidates.stream()
            .map(candidate -> {
                // 1. Recuperar contexto relevante do VectorStore
                var context = vectorStore.search(
                    candidate.getCourse().getDescription(),
                    5 // top-5 documentos similares
                );

                // 2. Construir prompt com RAG
                String prompt = buildPrompt(profile, candidate, context);

                // 3. Gerar explicação com LLM
                String explanation = llm.generate(prompt);

                return new ExplainedRecommendation(
                    candidate.getCourse(),
                    explanation,
                    candidate.getSimilarity()
                );
            })
            .collect(Collectors.toList());
    }

    private String buildPrompt(StudentProfile profile, RecommendedCourse candidate, List<String> context) {
        return String.format(
            """
            Você é um assistente de recomendação de cursos educacionais.
            
            Perfil do Estudante:
            - Nome: %s
            - Cursos Completados: %s
            - Nível: %s
            
            Curso Recomendado: %s
            Descrição: %s
            
            Contexto Relevante:
            %s
            
            Por favor, explique em 2-3 frases de forma natural e convincente por que este curso 
            é uma excelente próxima escolha para este estudante.
            """,
            profile.getName(),
            profile.getCompletedCourses(),
            profile.getLevel(),
            candidate.getCourse().getName(),
            candidate.getCourse().getDescription(),
            String.join("\n", context)
        );
    }
}
