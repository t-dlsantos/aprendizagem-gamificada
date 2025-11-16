package com.grupoenzo.aprendizagem_gamificada.presentation.controller.enrollment;

import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.FinalizeCourseUseCase;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class EnrollmentController implements EnrollmentResource {
    private FinalizeCourseUseCase finalizeCourseUseCase;

    @Override
    public ResponseEntity<Object> finalize(UUID id) {
        var result = finalizeCourseUseCase.execute(id);
        return ResponseEntity.ok(result);
    }
}