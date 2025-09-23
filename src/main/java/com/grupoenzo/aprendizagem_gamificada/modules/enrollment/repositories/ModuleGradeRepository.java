package com.grupoenzo.aprendizagem_gamificada.modules.enrollment.repositories;

import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.entities.ModuleGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ModuleGradeRepository extends JpaRepository<ModuleGradeEntity, UUID> {
    
    @Query("SELECT mg FROM module_grade mg WHERE mg.enrollment.id = :enrollmentId")
    List<ModuleGradeEntity> findByEnrollmentId(@Param("enrollmentId") UUID enrollmentId);
    
    @Query("SELECT mg FROM module_grade mg WHERE mg.student.id = :studentId AND mg.module.id = :moduleId")
    List<ModuleGradeEntity> findByStudentIdAndModuleId(@Param("studentId") UUID studentId, @Param("moduleId") UUID moduleId);
}