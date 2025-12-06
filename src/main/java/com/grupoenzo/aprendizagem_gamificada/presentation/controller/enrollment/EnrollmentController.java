package com.grupoenzo.aprendizagem_gamificada.presentation.controller.enrollment;

import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.FinalizeCourseUseCase;
import com.grupoenzo.aprendizagem_gamificada.presentation.dtos.responses.FinalizeCourseResponse;
import com.grupoenzo.aprendizagem_gamificada.presentation.dtos.requests.RecommendationRequest;
import com.grupoenzo.aprendizagem_gamificada.infra.http.RecommenderClient;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class EnrollmentController implements EnrollmentResource {
    private final FinalizeCourseUseCase finalizeCourseUseCase;
    private final RecommenderClient recommenderClient;

    public EnrollmentController(FinalizeCourseUseCase finalizeCourseUseCase, RecommenderClient recommenderClient) {
        this.finalizeCourseUseCase = finalizeCourseUseCase;
        this.recommenderClient = recommenderClient;
    }
    
    @Override
    public ResponseEntity<Object> finalize(UUID id) {
        try {
            var enrollment = finalizeCourseUseCase.execute(id);

            var enrolledCourseIds = enrollment.getStudent().getEnrollments() != null
                ? enrollment.getStudent().getEnrollments().stream()
                    .map(e -> e.getCourse().getId())
                    .collect(Collectors.toList())
                : java.util.Collections.<java.util.UUID>emptyList();

            var recommendation = recommenderClient.getRecommendation(
                new RecommendationRequest(
                    enrollment.getStudent().getId(),
                    enrollment.getCourse().getId(),
                    enrolledCourseIds,
                    enrollment.getCourse().getName()
                )
            );

        var response = new FinalizeCourseResponse(
            enrollment.getId(),
            enrollment.getStudent().getId(),
            enrollment.getCourse().getId(),
            enrollment.getStatus(),
            enrollment.calculateAverageGrade(),
            3
        );

        response.setRecommendation(recommendation);

        return ResponseEntity.ok(response);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
}