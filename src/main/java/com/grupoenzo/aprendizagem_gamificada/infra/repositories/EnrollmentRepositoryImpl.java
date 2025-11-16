package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.mappers.EnrollmentMapper;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaEnrollmentRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    private final JpaEnrollmentRepository jpaRepository;
    private final EnrollmentMapper mapper;

    public EnrollmentRepositoryImpl(JpaEnrollmentRepository jpaRepository, EnrollmentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Enrollment> findByStudentIdAndCourseId(UUID studentId, UUID courseId) {
        return jpaRepository.findByStudentIdAndCourseId(studentId, courseId).map(this.mapper::map);
    }

    @Override
    public Optional<Enrollment> findById(UUID id) {
        return jpaRepository.findById(id).map(this.mapper::map);
    }
}
