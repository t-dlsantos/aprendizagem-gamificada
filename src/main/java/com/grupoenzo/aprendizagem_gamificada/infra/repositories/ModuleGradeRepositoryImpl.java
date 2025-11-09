package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.ModuleGradeRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaModuleGradeRepository;

import java.util.List;
import java.util.UUID;

public class ModuleGradeRepositoryImpl implements ModuleGradeRepository {
    private final JpaModuleGradeRepository jpaRepository;

    public ModuleGradeRepositoryImpl(JpaModuleGradeRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<ModuleGrade> findByEnrollmentId(UUID enrollmentId) {
        return jpaRepository.findByEnrollmentId(enrollmentId)
                .map(this::toDomain);
    }

    @Override
    public List<ModuleGrade> findByStudentIdAndModuleId(UUID studentId, UUID moduleId) {
        return jpaRepository.findByStudentIdAndModuleId(studentId, moduleId)
                .map(this::toDomain);
    }
}
