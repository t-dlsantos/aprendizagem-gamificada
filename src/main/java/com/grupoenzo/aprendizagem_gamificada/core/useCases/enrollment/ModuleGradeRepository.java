package com.grupoenzo.aprendizagem_gamificada.core.useCases.enrollment;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ModuleGradeRepository {
    List<ModuleGradeEntity> findByEnrollmentId(UUID enrollmentId);
    List<ModuleGradeEntity> findByStudentIdAndModuleId(UUID studentId, UUID moduleId);
}