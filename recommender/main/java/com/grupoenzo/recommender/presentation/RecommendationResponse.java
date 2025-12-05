package com.grupoenzo.recommender.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Resposta com a recomendação de curso gerada")
public class RecommendationResponse {
    private UUID recommendedCourseId;
    private String courseName;
    private String explanation;
    private double relevanceScore;

    public RecommendationResponse(UUID id, String name, String explanation, double score) {
        this.recommendedCourseId = id;
        this.courseName = name;
        this.explanation = explanation;
        this.relevanceScore = score;
    }

    @Schema(description = "ID do curso recomendado", example = "123e4567-e89b-12d3-a456-426614174002")
    public UUID getRecommendedCourseId() { return recommendedCourseId; }
    
    @Schema(description = "Nome do curso recomendado", example = "Java Avançado")
    public String getCourseName() { return courseName; }
    
    @Schema(description = "Explicação gerada pelo LLM sobre por que este curso foi recomendado", 
            example = "Recomendado: com base no contexto, este curso parece uma boa escolha.")
    public String getExplanation() { return explanation; }
    
    @Schema(description = "Score de relevância da recomendação (0.0 a 1.0)", example = "0.85")
    public double getRelevanceScore() { return relevanceScore; }
}
