package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.CourseJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.EnrollmentJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.StudentJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaEnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaStudentRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaCourseRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaModuleGradeRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class EnrollmentRepositoryFindByIdIntegrationTest {

    @Autowired
    private JpaEnrollmentRepository jpaEnrollmentRepository;

    @Autowired
    private JpaStudentRepository jpaStudentRepository;

    @Autowired
    private JpaCourseRepository jpaCourseRepository;

    @Autowired
    private EnrollmentRepositoryImpl repository;

    @Test
    @Transactional
    void findById_mapsJpaEntityToDomain() {
        var student = StudentJpaEntity.builder()
                .name("Thiago")
                .ticket(new com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket(0))
                .build();
        jpaStudentRepository.save(student);

        var course = CourseJpaEntity.builder()
                .name("DevOps")
                .build();
        course.setModules(java.util.List.of());
        jpaCourseRepository.save(course);

        var enrollmentJpa = EnrollmentJpaEntity.builder()
                .student(student)
                .course(course)
                .build();

        jpaEnrollmentRepository.save(enrollmentJpa);

        var found = repository.findById(enrollmentJpa.getId());

        assertTrue(found.isPresent());
        Enrollment domain = found.get();
        assertEquals(enrollmentJpa.getId(), domain.getId());
        assertEquals("Thiago", domain.getStudent().getName());
        assertEquals("DevOps", domain.getCourse().getName());
    }
}
