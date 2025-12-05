package com.grupoenzo.aprendizagem_gamificada.presentation.dtos.requests;

import java.util.List;
import java.util.UUID;

public class RecommendationRequest {
    private UUID studentId;
    private UUID completedCourseId;
    private List<UUID> enrolledCourseIds;
    private String completedCourseName;

    public RecommendationRequest() {}
    public RecommendationRequest(UUID studentId, UUID completedCourseId, List<UUID> enrolledCourseIds, String completedCourseName) {
        this.studentId = studentId;
        this.completedCourseId = completedCourseId;
        this.enrolledCourseIds = enrolledCourseIds;
        this.completedCourseName = completedCourseName;
    }
    public UUID getStudentId() { return studentId; }
    public UUID getCompletedCourseId() { return completedCourseId; }
    public List<UUID> getEnrolledCourseIds() { return enrolledCourseIds; }
    public String getCompletedCourseName() { return completedCourseName; }
}
