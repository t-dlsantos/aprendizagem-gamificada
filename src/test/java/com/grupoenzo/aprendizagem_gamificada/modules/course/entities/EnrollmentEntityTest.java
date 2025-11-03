package com.grupoenzo.aprendizagem_gamificada.modules.course.entities;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleEntity;
import com.grupoenzo.aprendizagem_gamificada.exceptions.InsufficientCoursesCompletedException;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGradeEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.StudentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentEntityTest {

    private StudentEntity student;
    private CourseEntity course;
    private ModuleEntity module;
    private EnrollmentEntity enrollment;

    @BeforeEach
    void setup() {
        student = StudentEntity.builder().id(UUID.randomUUID()).name("Thiago").tickets(0).build();
        course = CourseEntity.builder().id(UUID.randomUUID()).name("DevOps").description("A course to master DevOps").build();
        module = ModuleEntity.builder().id(UUID.randomUUID()).name("Module 1").description("Unit tests").course(course).build();
        course.setModules(List.of(module));
        enrollment = EnrollmentEntity.builder().id(UUID.randomUUID()).course(course).student(student).build();
    }

    @Test
    @DisplayName("Should calculate average grade correctly with one module")
    void shouldCalculateAverageGrade() {
        ModuleGradeEntity moduleGrade = ModuleGradeEntity.builder()
                .student(student)
                .module(module)
                .enrollment(enrollment)
                .grade(8.0)
                .build();
        enrollment.setModuleGrades(List.of(moduleGrade));

        double average = enrollment.calculateAverageGrade();
        assertEquals(8.0, average);
    }

    @Test
    @DisplayName("Should throw InsufficientCoursesCompletedException when moduleGrades is null")
    void shouldThrowWhenModuleGradesIsNull() {
        enrollment.setModuleGrades(null);
        assertThrows(InsufficientCoursesCompletedException.class, enrollment::calculateAverageGrade);
    }

    @Test
    @DisplayName("Should throw InsufficientCoursesCompletedException when moduleGrades is empty")
    void shouldThrowWhenModuleGradesIsEmpty() {
        enrollment.setModuleGrades(List.of());
        assertThrows(InsufficientCoursesCompletedException.class, enrollment::calculateAverageGrade);
    }

    @Test
    @DisplayName("Should throw InsufficientCoursesCompletedException when moduleGrades.size() < course.modules.size()")
    void shouldThrowWhenModuleGradesIsLessThanCourseModules() {
        enrollment.setModuleGrades(List.of());
        assertThrows(InsufficientCoursesCompletedException.class, enrollment::calculateAverageGrade);
    }

    @Test
    @DisplayName("Should calculate average grade correctly with multiple modules")
    void shouldCalculateAverageGradeWithMultipleModules() {
        ModuleEntity module2 = ModuleEntity.builder().id(UUID.randomUUID()).name("Module 2").course(course).build();
        course.setModules(List.of(module, module2));

        ModuleGradeEntity grade1 = ModuleGradeEntity.builder().student(student).module(module).enrollment(enrollment).grade(6.0).build();
        ModuleGradeEntity grade2 = ModuleGradeEntity.builder().student(student).module(module2).enrollment(enrollment).grade(8.0).build();
        enrollment.setModuleGrades(List.of(grade1, grade2));

        double average = enrollment.calculateAverageGrade();
        assertEquals(7.0, average);
    }

    @Test
    @DisplayName("Should throw InsufficientCoursesCompletedException when moduleGrades.size() is different from course.modules.size()")
    void shouldThrowWhenModuleGradesSizeDoesNotMatchCourseModules() {
        ModuleEntity module2 = ModuleEntity.builder()
                .id(UUID.randomUUID())
                .name("Module 2")
                .course(course)
                .build();
        course.setModules(List.of(module, module2));

        ModuleGradeEntity grade1 = ModuleGradeEntity.builder()
                .student(student)
                .module(module)
                .enrollment(enrollment)
                .grade(9.0)
                .build();
        enrollment.setModuleGrades(List.of(grade1));

        assertThrows(InsufficientCoursesCompletedException.class, enrollment::calculateAverageGrade);
    }

}