package com.grupoenzo.aprendizagem_gamificada.presentation.dtos.responses;

import com.grupoenzo.aprendizagem_gamificada.core.domain.enums;
import java.util.UUID;

public class FinalizeCourseResponse {
    private UUID enrollmentId;
    private UUID studentId;
    private UUID courseId;
    private EnrollmentStatus status;
    private Double averageGrade;
    private Integer ticketsAdded;
    private RecommendationResponse recommendation;

    public FinalizeCourseResponse(UUID enrollmentId, UUID studentId, UUID courseId, 
                                   EnrollmentStatus status, Double averageGrade, Integer ticketsAdded) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.status = status;
        this.averageGrade = averageGrade;
        this.ticketsAdded = ticketsAdded;
    }

    public UUID getEnrollmentId() { return enrollmentId; }
    public UUID getStudentId() { return studentId; }
    public UUID getCourseId() { return courseId; }
    public EnrollmentStatus getStatus() { return status; }
    public Double getAverageGrade() { return averageGrade; }
    public Integer getTicketsAdded() { return ticketsAdded; }
    public RecommendationResponse getRecommendation() { return recommendation; }

    public void setRecommendation(RecommendationResponse recommendation) {
        this.recommendation = recommendation;
    }
}