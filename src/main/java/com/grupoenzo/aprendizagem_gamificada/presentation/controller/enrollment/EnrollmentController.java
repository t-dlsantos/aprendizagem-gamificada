package com.grupoenzo.aprendizagem_gamificada.presentation.controller.enrollment;

import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.FinalizeCourseUseCase;
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
            var result = finalizeCourseUseCase.execute(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}