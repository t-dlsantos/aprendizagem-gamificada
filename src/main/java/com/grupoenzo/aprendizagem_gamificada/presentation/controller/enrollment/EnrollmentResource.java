package com.grupoenzo.aprendizagem_gamificada.presentation.controller.enrollment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@RestController
@RequestMapping("enrollment")
@Tag(name = "Enrollment ", description = "Enrollment's informations and actions")
public interface EnrollmentResource {
    @PostMapping("/{id}/finalize")
    ResponseEntity<Object> finalize(@PathVariable UUID id);
}