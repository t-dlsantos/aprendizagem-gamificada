package com.grupoenzo.aprendizagem_gamificada.presentation.controller.enrollment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("enrollment")
public interface EnrollmentResource {
    @PostMapping("/{id}/finalize")
    ResponseEntity<Object> finalize(@PathVariable UUID id);
}