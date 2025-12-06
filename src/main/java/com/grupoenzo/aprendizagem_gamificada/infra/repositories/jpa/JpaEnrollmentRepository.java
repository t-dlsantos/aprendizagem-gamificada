package com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa;

import com.grupoenzo.aprendizagem_gamificada.infra.entity.EnrollmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaEnrollmentRepository extends JpaRepository<EnrollmentJpaEntity, UUID> {
    @Query("SELECT e FROM enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    Optional<EnrollmentJpaEntity> findByStudentIdAndCourseId(
        @Param("studentId") UUID studentId,
        @Param("courseId") UUID courseId
    );

    @Query("SELECT e FROM enrollment e LEFT JOIN FETCH e.moduleGrades WHERE e.student.id = :studentId AND e.course.id = :courseId")
    Optional<EnrollmentJpaEntity> findByStudentIdAndCourseIdWithGrades(@Param("studentId") UUID studentId, @Param("courseId") UUID courseId);

    @Query("SELECT e FROM enrollment e LEFT JOIN FETCH e.moduleGrades WHERE e.id = :id")
    Optional<EnrollmentJpaEntity> findByIdWithGrades(@Param("id") UUID id);
}