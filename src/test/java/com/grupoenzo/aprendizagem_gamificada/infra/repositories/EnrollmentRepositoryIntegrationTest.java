package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.enums.EnrollmentStatus;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.CourseJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.EnrollmentJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.StudentJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaCourseRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaEnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaStudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class EnrollmentRepositoryIntegrationTest {

    @Autowired
    private JpaStudentRepository jpaStudentRepository;

    @Autowired
    private JpaCourseRepository jpaCourseRepository;

    @Autowired
    private JpaEnrollmentRepository jpaEnrollmentRepository;

    @Autowired
    private EnrollmentRepositoryImpl enrollmentRepository;

        @Test
        @Transactional
        void findByStudentIdAndCourseId_shouldReturnMappedDomainEnrollment() {
        StudentJpaEntity student = StudentJpaEntity.builder()
                .name("Integration Student")
                .build();
        student = jpaStudentRepository.save(student);

        CourseJpaEntity course = CourseJpaEntity.builder()
                .name("Integration Course")
                .description("desc")
                .build();
        // ensure modules list is non-null (Lombok @Builder doesn't apply field defaults)
        course.setModules(java.util.List.of());
        course = jpaCourseRepository.save(course);

        EnrollmentJpaEntity enrollmentJpa = EnrollmentJpaEntity.builder()
                .student(student)
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .build();
        enrollmentJpa = jpaEnrollmentRepository.save(enrollmentJpa);

        Optional<Enrollment> found = enrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId());

        assertTrue(found.isPresent(), "Enrollment should be found by student and course ids");

        Enrollment domain = found.get();
        assertNotNull(domain.getId());
        assertEquals(enrollmentJpa.getId(), domain.getId());
        assertEquals(student.getId(), domain.getStudent().getId());
        assertEquals(course.getId(), domain.getCourse().getId());
    }
}
