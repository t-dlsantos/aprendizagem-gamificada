package com.grupoenzo.aprendizagem_gamificada.modules.enrollment.repositories;

import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.student.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, UUID> {
    @Query("SELECT e FROM enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    Optional<EnrollmentEntity> findByStudentIdAndCourseId(@Param("studentId") UUID studentId,
                                                          @Param("courseId") UUID courseId);
}