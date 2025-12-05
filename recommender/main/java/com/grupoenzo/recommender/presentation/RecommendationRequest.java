package com.grupoenzo.recommender.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;

@Schema(description = "Requisição para obter recomendação de curso")
public class RecommendationRequest {
    private UUID studentId;
    private UUID completedCourseId;
    private List<UUID> enrolledCourseIds;
    private String completedCourseName;

    public RecommendationRequest() {}
    
    @Schema(description = "ID do estudante", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    public UUID getStudentId() { return studentId; }
    
    @Schema(description = "ID do curso recém completado", example = "123e4567-e89b-12d3-a456-426614174001", required = true)
    public UUID getCompletedCourseId() { return completedCourseId; }
    
    @Schema(description = "Lista de IDs dos cursos em que o estudante está matriculado", required = false)
    public List<UUID> getEnrolledCourseIds() { return enrolledCourseIds; }
    
    @Schema(description = "Nome do curso completado", example = "Java Básico", required = false)
    public String getCompletedCourseName() { return completedCourseName; }
    
    public void setStudentId(UUID studentId) { this.studentId = studentId; }
    public void setCompletedCourseId(UUID completedCourseId) { this.completedCourseId = completedCourseId; }
    public void setEnrolledCourseIds(List<UUID> enrolledCourseIds) { this.enrolledCourseIds = enrolledCourseIds; }
    public void setCompletedCourseName(String completedCourseName) { this.completedCourseName = completedCourseName; }
}
