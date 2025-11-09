package com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;

import java.util.List;
import java.util.UUID;

public interface ModuleGradeRepository {
    List<ModuleGrade> findByEnrollmentId(UUID enrollmentId);
    List<ModuleGrade> findByStudentIdAndModuleId(UUID studentId, UUID moduleId);
}