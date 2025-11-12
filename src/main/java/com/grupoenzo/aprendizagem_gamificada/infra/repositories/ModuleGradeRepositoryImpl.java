package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.repositories.ModuleGradeRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.mappers.ModuleGradeMapper;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaModuleGradeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ModuleGradeRepositoryImpl implements ModuleGradeRepository {
    private final JpaModuleGradeRepository jpaRepository;
    private final ModuleGradeMapper mapper;

    public ModuleGradeRepositoryImpl(JpaModuleGradeRepository jpaRepository, ModuleGradeMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ModuleGrade> findByEnrollmentId(UUID enrollmentId) {
        return mapper.map(jpaRepository.findByEnrollmentId(enrollmentId));
    }

    @Override
    public List<ModuleGrade> findByStudentIdAndModuleId(UUID studentId, UUID moduleId) {
        return mapper.map(jpaRepository.findByStudentIdAndModuleId(studentId, moduleId));
    }
}
