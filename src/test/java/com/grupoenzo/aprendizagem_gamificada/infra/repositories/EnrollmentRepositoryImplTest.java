package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.infra.mappers.EnrollmentMapper;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaEnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.EnrollmentJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentRepositoryImplTest {

    @Mock
    private JpaEnrollmentRepository jpaRepository;

    @Mock
    private EnrollmentMapper mapper;

    private EnrollmentRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new EnrollmentRepositoryImpl(jpaRepository, mapper);
    }

    @Test
    void findByStudentIdAndCourseId_ShouldReturnEnrollment() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        EnrollmentJpaEntity jpaEntity = new EnrollmentJpaEntity();
        Enrollment enrollment = new Enrollment(UUID.randomUUID(), null, null);

        when(jpaRepository.findByStudentIdAndCourseId(studentId, courseId)).thenReturn(Optional.of(jpaEntity));
        when(mapper.map(jpaEntity)).thenReturn(enrollment);

        Optional<Enrollment> result = repository.findByStudentIdAndCourseId(studentId, courseId);

        assertTrue(result.isPresent());
        assertEquals(enrollment, result.get());
    }

    @Test
    void findById_ShouldReturnEnrollment() {
        UUID id = UUID.randomUUID();
        EnrollmentJpaEntity jpaEntity = new EnrollmentJpaEntity();
        Enrollment enrollment = new Enrollment(id, null, null);

        when(jpaRepository.findById(id)).thenReturn(Optional.of(jpaEntity));
        when(mapper.map(jpaEntity)).thenReturn(enrollment);

        Optional<Enrollment> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(enrollment, result.get());
    }

    @Test
    void findByStudentIdAndCourseIdWithGrades_ShouldReturnEnrollment() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        EnrollmentJpaEntity jpaEntity = new EnrollmentJpaEntity();
        Enrollment enrollment = new Enrollment(UUID.randomUUID(), null, null);

        when(jpaRepository.findByStudentIdAndCourseIdWithGrades(studentId, courseId)).thenReturn(Optional.of(jpaEntity));
        when(mapper.map(jpaEntity)).thenReturn(enrollment);

        Optional<Enrollment> result = repository.findByStudentIdAndCourseIdWithGrades(studentId, courseId);

        assertTrue(result.isPresent());
        assertEquals(enrollment, result.get());
    }

    @Test
    void findByIdWithGrades_ShouldReturnEnrollment() {
        UUID id = UUID.randomUUID();
        EnrollmentJpaEntity jpaEntity = new EnrollmentJpaEntity();
        Enrollment enrollment = new Enrollment(id, null, null);

        when(jpaRepository.findByIdWithGrades(id)).thenReturn(Optional.of(jpaEntity));
        when(mapper.map(jpaEntity)).thenReturn(enrollment);

        Optional<Enrollment> result = repository.findByIdWithGrades(id);

        assertTrue(result.isPresent());
        assertEquals(enrollment, result.get());
    }
}
