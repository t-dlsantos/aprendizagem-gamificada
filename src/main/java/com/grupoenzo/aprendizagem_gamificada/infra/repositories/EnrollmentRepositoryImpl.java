package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.core.useCases.enrollment.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaEnrollmentRepository;

import java.util.Optional;
import java.util.UUID;

public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    private final JpaEnrollmentRepository jpaRepository;

    public EnrollmentRepositoryImpl(JpaEnrollmentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<EnrollmentEntity> findByStudentIdAndCourseId(UUID studentId, UUID courseId) {
        return jpaRepository.findByStudentIdAndCourseId(studentId, courseId)
                .map(this::toDomain);
    }
}
