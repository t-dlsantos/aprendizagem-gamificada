package com.grupoenzo.recommender.presentation;

import java.util.List;
import java.util.UUID;

public class RecommendationRequest {
    private UUID studentId;
    private UUID completedCourseId;
    private List<UUID> enrolledCourseIds;
    private String completedCourseName;

    public RecommendationRequest() {}
    public UUID getStudentId() { return studentId; }
    public UUID getCompletedCourseId() { return completedCourseId; }
    public List<UUID> getEnrolledCourseIds() { return enrolledCourseIds; }
    public String getCompletedCourseName() { return completedCourseName; }
}
