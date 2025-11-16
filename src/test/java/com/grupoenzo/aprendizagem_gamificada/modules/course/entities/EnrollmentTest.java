package com.grupoenzo.aprendizagem_gamificada.modules.course.entities;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Module;
import com.grupoenzo.aprendizagem_gamificada.core.domain.exceptions.InsufficientCoursesCompletedException;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentTest {

    private Student student;
    private CourseEntity course;
    private Module module;
    private Enrollment enrollment;

    @BeforeEach
    void setup() {
        student = Student.builder().id(UUID.randomUUID()).name("Thiago").tickets(0).build();
        course = CourseEntity.builder().id(UUID.randomUUID()).name("DevOps").description("A course to master DevOps").build();
        module = Module.builder().id(UUID.randomUUID()).name("Module 1").description("Unit tests").course(course).build();
        course.setModules(List.of(module));
        enrollment = Enrollment.builder().id(UUID.randomUUID()).course(course).student(student).build();
    }

    @Test
    @DisplayName("Should calculate average grade correctly with one module")
    void shouldCalculateAverageGrade() {
        ModuleGrade moduleGrade = ModuleGrade.builder()
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
        Module module2 = Module.builder().id(UUID.randomUUID()).name("Module 2").course(course).build();
        course.setModules(List.of(module, module2));

        ModuleGrade grade1 = ModuleGrade.builder().student(student).module(module).enrollment(enrollment).grade(6.0).build();
        ModuleGrade grade2 = ModuleGrade.builder().student(student).module(module2).enrollment(enrollment).grade(8.0).build();
        enrollment.setModuleGrades(List.of(grade1, grade2));

        double average = enrollment.calculateAverageGrade();
        assertEquals(7.0, average);
    }

    @Test
    @DisplayName("Should throw InsufficientCoursesCompletedException when moduleGrades.size() is different from course.modules.size()")
    void shouldThrowWhenModuleGradesSizeDoesNotMatchCourseModules() {
        Module module2 = Module.builder()
                .id(UUID.randomUUID())
                .name("Module 2")
                .course(course)
                .build();
        course.setModules(List.of(module, module2));

        ModuleGrade grade1 = ModuleGrade.builder()
                .student(student)
                .module(module)
                .enrollment(enrollment)
                .grade(9.0)
                .build();
        enrollment.setModuleGrades(List.of(grade1));

        assertThrows(InsufficientCoursesCompletedException.class, enrollment::calculateAverageGrade);
    }

}