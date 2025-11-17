package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaModuleGradeRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaStudentRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaCourseRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaEnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.CourseJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.EnrollmentJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.ModuleGradeJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.ModuleJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.StudentJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class ModuleGradeRepositoryIntegrationTest {


        @Autowired
        private JpaModuleGradeRepository jpaModuleGradeRepository;

        @Autowired
        private JpaStudentRepository jpaStudentRepository;

        @Autowired
        private JpaCourseRepository jpaCourseRepository;

        @Autowired
        private JpaEnrollmentRepository jpaEnrollmentRepository;

    @Autowired
    private ModuleGradeRepositoryImpl repository;

    @Test
    @Transactional
    void findByEnrollmentId_and_findByStudentIdAndModuleId_work() {
        var student = StudentJpaEntity.builder()
                .name("Thiago")
                .ticket(new com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket(0))
                .build();
        jpaStudentRepository.save(student);

        var course = CourseJpaEntity.builder()
                .name("DevOps")
                .build();
        course.setModules(new java.util.ArrayList<>());
        course = jpaCourseRepository.save(course);

        var enrollment = EnrollmentJpaEntity.builder()
                .student(student)
                .course(course)
                .build();
        jpaEnrollmentRepository.save(enrollment);

        var module = ModuleJpaEntity.builder()
                .name("M1")
                .course(course)
                .build();
        // persist module via course cascade
        course.getModules().add(module);
        course = jpaCourseRepository.save(course);
        // obtain the managed module instance (with id)
        module = course.getModules().stream().filter(m -> "M1".equals(m.getName())).findFirst().orElse(module);

        var mg = ModuleGradeJpaEntity.builder()
                .grade(9.0)
                .student(student)
                .enrollment(enrollment)
                .module(module)
                .build();

        jpaModuleGradeRepository.save(mg);

        List<ModuleGrade> byEnrollment = repository.findByEnrollmentId(enrollment.getId());
        assertNotNull(byEnrollment);
        assertFalse(byEnrollment.isEmpty());

        List<ModuleGrade> byStudentModule = repository.findByStudentIdAndModuleId(student.getId(), module.getId());
        assertNotNull(byStudentModule);
        assertFalse(byStudentModule.isEmpty());
    }
}
