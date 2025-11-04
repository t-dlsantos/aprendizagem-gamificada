package com.grupoenzo.aprendizagem_gamificada.modules.enrollment.useCases;

import com.grupoenzo.aprendizagem_gamificada.core.useCases.enrollment.FinalizeCourseUseCase;
import com.grupoenzo.aprendizagem_gamificada.exceptions.EnrollmentNotFoundException;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGradeEntity;
import com.grupoenzo.aprendizagem_gamificada.core.useCases.enrollment.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.StudentEntity;
import com.grupoenzo.aprendizagem_gamificada.core.useCases.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FinalizeCourseUseCaseTest {
    @Mock
    private StudentRepository StudentRepository;
    @Mock
    private EnrollmentRepository EnrollmentRepository;
    @InjectMocks
    private FinalizeCourseUseCase finalizeCourseUseCase;

    private StudentEntity student;
    private CourseEntity course;
    private ModuleEntity module;
    private EnrollmentEntity enrollment;

    @BeforeEach
    public void setup() {
        this.student = StudentEntity.builder().id(UUID.randomUUID()).name("Thiago").tickets(0).build();
        this.course = CourseEntity.builder().id(UUID.randomUUID()).name("DevOps").description("A course to master DevOps").build();
        this.module = ModuleEntity.builder()
                .id(UUID.randomUUID())
                .name("Module 1")
                .description("Unit tests")
                .course(course)
                .build();
        this.course.setModules(List.of(module));
        this.enrollment = EnrollmentEntity.builder().id(UUID.randomUUID()).course(course).student(student).build();
    }

    @Test
    @DisplayName("Should release 3 more tickets when average grade equals to 7")
    public void shouldReleaseTicketsWhenAverageGradeIs7() {
        ModuleGradeEntity moduleGrade = ModuleGradeEntity.builder()
                .student(student)
                .module(module)
                .enrollment(enrollment)
                .grade(7.0)
                .build();
        enrollment.setModuleGrades(List.of(moduleGrade));

        when(EnrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId())).thenReturn(Optional.of(enrollment));

        var ticketsBefore = student.getTickets();
        var ticketsAfter = finalizeCourseUseCase.execute(student.getId(), course.getId()).getStudent().getTickets();

        assertEquals(ticketsBefore + 3, ticketsAfter);
    }

    @Test
    @DisplayName("Should release 3 more tickets when average grade is above 7")
    public void shouldReleaseTicketsWhenAverageGradeIsAbove7() {
        ModuleGradeEntity moduleGrade = ModuleGradeEntity.builder()
                .student(student)
                .module(module)
                .enrollment(enrollment)
                .grade(8.0)
                .build();
        enrollment.setModuleGrades(List.of(moduleGrade));

        when(EnrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId())).thenReturn(Optional.of(enrollment));

        var ticketsBefore = student.getTickets();
        var ticketsAfter = finalizeCourseUseCase.execute(student.getId(), course.getId()).getStudent().getTickets();

        assertEquals(ticketsBefore + 3, ticketsAfter);
    }

    @Test
    @DisplayName("Should not release 3 more tickets when average grade is below 7")
    public void shouldNotReleaseTicketsWhenAverageGradeIsBelow7() {
        ModuleGradeEntity moduleGrade = ModuleGradeEntity.builder()
                .student(student)
                .module(module)
                .enrollment(enrollment)
                .grade(5.0)
                .build();
        enrollment.setModuleGrades(List.of(moduleGrade));

        when(EnrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId())).thenReturn(Optional.of(enrollment));

        var ticketsBefore = student.getTickets();
        var ticketsAfter = finalizeCourseUseCase.execute(student.getId(), course.getId()).getStudent().getTickets();

        assertEquals(ticketsBefore, ticketsAfter);
    }

    @Test
    @DisplayName("Should throw EnrollmentNotFoundException when enrollment not found")
    void shouldThrowEnrollmentNotFoundExceptionWhenEnrollmentNotFound() {
        assertThrows(EnrollmentNotFoundException.class, () -> finalizeCourseUseCase.execute(student.getId(), course.getId()));
    }
}