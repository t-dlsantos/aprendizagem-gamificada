package com.grupoenzo.recommender.core.domain;

public class RecommendedCourse {
    private final Course course;
    private final double similarity;

    public RecommendedCourse(Course course, double similarity) {
        this.course = course;
        this.similarity = similarity;
    }

    public Course getCourse() { return course; }
    public double getSimilarity() { return similarity; }
}
