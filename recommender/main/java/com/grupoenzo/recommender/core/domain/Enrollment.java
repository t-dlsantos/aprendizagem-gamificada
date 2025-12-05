package com.grupoenzo.recommender.core.domain;

public class Enrollment {
    private Course course;

    public Enrollment(Course course) { this.course = course; }
    public Course getCourse() { return course; }
}
