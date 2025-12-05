package com.grupoenzo.aprendizagem_gamificada.presentation.dtos.responses;

import java.util.UUID;

public class RecommendationResponse {
    private UUID recommendedCourseId;
    private String courseName;
    private String explanation;
    private double relevanceScore;

    public RecommendationResponse() {}
    public UUID getRecommendedCourseId() { return recommendedCourseId; }
    public String getCourseName() { return courseName; }
    public String getExplanation() { return explanation; }
    public double getRelevanceScore() { return relevanceScore; }
    public void setRecommendedCourseId(UUID id) { this.recommendedCourseId = id; }
    public void setCourseName(String n) { this.courseName = n; }
    public void setExplanation(String e) { this.explanation = e; }
    public void setRelevanceScore(double s) { this.relevanceScore = s; }
}
