package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGradeEntity;
import com.grupoenzo.aprendizagem_gamificada.core.useCases.enrollment.ModuleGradeRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaModuleGradeRepository;

import java.util.List;
import java.util.UUID;

public class ModuleGradeRepositoryImpl implements ModuleGradeRepository {
    private final JpaModuleGradeRepository jpaRepository;

    public ModuleGradeRepositoryImpl(JpaModuleGradeRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<ModuleGradeEntity> findByEnrollmentId(UUID enrollmentId) {
        return jpaRepository.findByEnrollmentId(enrollmentId)
                .map(this::toDomain);
    }

    @Override
    public List<ModuleGradeEntity> findByStudentIdAndModuleId(UUID studentId, UUID moduleId) {
        return jpaRepository.findByStudentIdAndModuleId(studentId, moduleId)
                .map(this::toDomain);
    }
}
