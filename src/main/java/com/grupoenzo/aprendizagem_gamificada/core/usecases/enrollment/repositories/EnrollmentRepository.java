package com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;

import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository {
    Optional<Enrollment> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
    Optional<Enrollment> findById(UUID id);
    Optional<Enrollment> findByIdWithGrades(UUID id);
    Optional<Enrollment> findByStudentIdAndCourseIdWithGrades(UUID studentId, UUID courseId);
}