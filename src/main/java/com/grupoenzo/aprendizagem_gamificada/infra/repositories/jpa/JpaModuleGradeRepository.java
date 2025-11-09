package com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaModuleGradeRepository extends JpaRepository<ModuleGrade, UUID> {
    
    @Query("SELECT mg FROM module_grade mg WHERE mg.enrollment.id = :enrollmentId")
    List<ModuleGrade> findByEnrollmentId(@Param("enrollmentId") UUID enrollmentId);
    
    @Query("SELECT mg FROM module_grade mg WHERE mg.student.id = :studentId AND mg.module.id = :moduleId")
    List<ModuleGrade> findByStudentIdAndModuleId(@Param("studentId") UUID studentId, @Param("moduleId") UUID moduleId);
}