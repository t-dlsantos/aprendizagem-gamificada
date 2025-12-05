package com.grupoenzo.recommender.core.domain;

public class ExplainedRecommendation {
    private final Course course;
    private final String explanation;
    private final double relevanceScore;

    public ExplainedRecommendation(Course course, String explanation, double relevanceScore) {
        this.course = course;
        this.explanation = explanation;
        this.relevanceScore = relevanceScore;
    }

    public Course getCourse() { return course; }
    public String getExplanation() { return explanation; }
    public double getRelevanceScore() { return relevanceScore; }
}
