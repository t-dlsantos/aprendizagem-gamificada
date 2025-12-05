package com.grupoenzo.recommender.presentation;

import java.util.UUID;

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

    public UUID getRecommendedCourseId() { return recommendedCourseId; }
    public String getCourseName() { return courseName; }
    public String getExplanation() { return explanation; }
    public double getRelevanceScore() { return relevanceScore; }
}
