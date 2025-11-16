package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Module;
import com.grupoenzo.aprendizagem_gamificada.core.exceptions.InsufficientModulesCompletedException;
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
    private Course course;
    private Module module;
    private Enrollment enrollment;

    @BeforeEach
    void setup() {
        student = new Student(UUID.randomUUID(), "Thiago", new com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket(0));
        course = new Course(UUID.randomUUID(), "DevOps");
        course.setDescription("A course to master DevOps");
        module = new Module(UUID.randomUUID(), "Module 1", "Unit tests", course);
        course.setModules(List.of(module));
        enrollment = new Enrollment(UUID.randomUUID(), student, course);
    }

    @Test
    @DisplayName("Should calculate average grade correctly with one module")
    void shouldCalculateAverageGrade() {
        ModuleGrade moduleGrade = new ModuleGrade(UUID.randomUUID(), module, student, enrollment, 8.0);
        enrollment.setModuleGrades(List.of(moduleGrade));

        double average = enrollment.calculateAverageGrade();
        assertEquals(8.0, average);
    }

    @Test
    @DisplayName("Should throw InsufficientCoursesCompletedException when moduleGrades is null")
    void shouldThrowWhenModuleGradesIsNull() {
        enrollment.setModuleGrades(null);
        assertThrows(InsufficientModulesCompletedException.class, enrollment::calculateAverageGrade);
    }

    @Test
    @DisplayName("Should throw InsufficientCoursesCompletedException when moduleGrades is empty")
    void shouldThrowWhenModuleGradesIsEmpty() {
        enrollment.setModuleGrades(List.of());
        assertThrows(InsufficientModulesCompletedException.class, enrollment::calculateAverageGrade);
    }

    @Test
    @DisplayName("Should throw InsufficientCoursesCompletedException when moduleGrades.size() < course.modules.size()")
    void shouldThrowWhenModuleGradesIsLessThanCourseModules() {
        enrollment.setModuleGrades(List.of());
        assertThrows(InsufficientModulesCompletedException.class, enrollment::calculateAverageGrade);
    }

    @Test
    @DisplayName("Should calculate average grade correctly with multiple modules")
    void shouldCalculateAverageGradeWithMultipleModules() {
        Module module2 = new Module(UUID.randomUUID(), "Module 2", null, course);
        course.setModules(List.of(module, module2));

        ModuleGrade grade1 = new ModuleGrade(UUID.randomUUID(), module, student, enrollment, 6.0);
        ModuleGrade grade2 = new ModuleGrade(UUID.randomUUID(), module2, student, enrollment, 8.0);
        enrollment.setModuleGrades(List.of(grade1, grade2));

        double average = enrollment.calculateAverageGrade();
        assertEquals(7.0, average);
    }

    @Test
    @DisplayName("Should throw InsufficientCoursesCompletedException when moduleGrades.size() is different from course.modules.size()")
    void shouldThrowWhenModuleGradesSizeDoesNotMatchCourseModules() {
        Module module2 = new Module(UUID.randomUUID(), "Module 2", null, course);
        course.setModules(List.of(module, module2));

        ModuleGrade grade1 = new ModuleGrade(UUID.randomUUID(), module, student, enrollment, 9.0);
        enrollment.setModuleGrades(List.of(grade1));

        assertThrows(InsufficientModulesCompletedException.class, enrollment::calculateAverageGrade);
    }

}
