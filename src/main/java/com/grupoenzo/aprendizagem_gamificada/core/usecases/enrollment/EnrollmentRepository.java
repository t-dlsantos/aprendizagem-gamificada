package com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;

import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository {
    Optional<Enrollment> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
}