package com.grupoenzo.aprendizagem_gamificada.core.useCases.enrollment;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository {
    Optional<EnrollmentEntity> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
}