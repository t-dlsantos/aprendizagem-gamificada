package com.grupoenzo.aprendizagem_gamificada.presentation.controller.enrollment;

import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.FinalizeCourseUseCase;
import com.grupoenzo.aprendizagem_gamificada.presentation.dtos.responses.FinalizeCourseResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class EnrollmentController implements EnrollmentResource {
    private FinalizeCourseUseCase finalizeCourseUseCase;

    public EnrollmentController(FinalizeCourseUseCase finalizeCourseUseCase) {
        this.finalizeCourseUseCase = finalizeCourseUseCase;
    }
    
    @Override
public ResponseEntity<Object> finalize(UUID id) {
    try {
        var enrollment = finalizeCourseUseCase.execute(id);

        var recommendation = recommenderClient.getRecommendation(
            new RecommendationRequest(
                enrollment.getStudent().getId(),
                enrollment.getCourse().getId(),
                enrollment.getStudent().getEnrollments(),
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