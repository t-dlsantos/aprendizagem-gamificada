package com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment;

import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.FinalizeCourseUseCase;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.student.repositories.StudentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.exceptions.EnrollmentNotFoundException;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Module;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class FinalizeCourseUseCaseTest {
    @Mock
    private StudentRepository StudentRepository;
    @Mock
    private EnrollmentRepository EnrollmentRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private FinalizeCourseUseCase finalizeCourseUseCase;

    private Student student;
    private Course course;
    private Module module;
    private Enrollment enrollment;

    @BeforeEach
    public void setup() {
        this.student = new Student(UUID.randomUUID(), "Thiago", new Ticket(0));
        this.course = new Course(UUID.randomUUID(), "DevOps");
        this.course.setDescription("A course to master DevOps");
        this.module = new Module(UUID.randomUUID(), "Module 1", "Unit tests", course);
        this.course.setModules(List.of(module));
        this.enrollment = new Enrollment(UUID.randomUUID(), student, course);
    }

    @Test
    @DisplayName("Should finalize enrollment by id and release tickets when average >= 7")
    public void shouldFinalizeByIdWhenAverageIs7OrAbove() {
        ModuleGrade moduleGrade = new ModuleGrade(UUID.randomUUID(), module, student, enrollment, 7.0);
        enrollment.setModuleGrades(List.of(moduleGrade));

        when(EnrollmentRepository.findByIdWithGrades(enrollment.getId())).thenReturn(Optional.of(enrollment));

        var ticketsBefore = student.getTicket().getValue();
        var result = finalizeCourseUseCase.execute(enrollment.getId());

        var ticketsAfter = result.getStudent().getTicket().getValue();
        assertEquals(ticketsBefore + 3, ticketsAfter);
        assertEquals(com.grupoenzo.aprendizagem_gamificada.core.domain.enums.EnrollmentStatus.COMPLETED, result.getStatus());
        
        // Verificar que o evento foi publicado
        verify(eventPublisher, times(1)).publishEvent(any());
    }

    @Test
    @DisplayName("Should throw InsufficientGradeException when finalizing by id and average < 7")
    public void shouldThrowWhenFinalizingByIdAndAverageBelow7() {
        ModuleGrade moduleGrade = new ModuleGrade(UUID.randomUUID(), module, student, enrollment, 5.0);
        enrollment.setModuleGrades(List.of(moduleGrade));

        when(EnrollmentRepository.findByIdWithGrades(enrollment.getId())).thenReturn(Optional.of(enrollment));

        org.junit.jupiter.api.Assertions.assertThrows(com.grupoenzo.aprendizagem_gamificada.core.exceptions.InsufficientGradeException.class,
                () -> finalizeCourseUseCase.execute(enrollment.getId()));
    }

    @Test
    @DisplayName("Should release 3 more tickets when average grade equals to 7")
    public void shouldReleaseTicketsWhenAverageGradeIs7() {
        ModuleGrade moduleGrade = new ModuleGrade(UUID.randomUUID(), module, student, enrollment, 7.0);
        enrollment.setModuleGrades(List.of(moduleGrade));

        when(EnrollmentRepository.findByStudentIdAndCourseIdWithGrades(student.getId(), course.getId())).thenReturn(Optional.of(enrollment));

        var ticketsBefore = student.getTicket().getValue();
        var ticketsAfter = finalizeCourseUseCase.execute(student.getId(), course.getId()).getStudent().getTicket().getValue();

        assertEquals(ticketsBefore + 3, ticketsAfter);
        
        // Verificar que o evento foi publicado
        verify(eventPublisher, times(1)).publishEvent(any());
    }

    @Test
    @DisplayName("Should release 3 more tickets when average grade is above 7")
    public void shouldReleaseTicketsWhenAverageGradeIsAbove7() {
        ModuleGrade moduleGrade = new ModuleGrade(UUID.randomUUID(), module, student, enrollment, 8.0);
        enrollment.setModuleGrades(List.of(moduleGrade));

        when(EnrollmentRepository.findByStudentIdAndCourseIdWithGrades(student.getId(), course.getId())).thenReturn(Optional.of(enrollment));

        var ticketsBefore = student.getTicket().getValue();
        var ticketsAfter = finalizeCourseUseCase.execute(student.getId(), course.getId()).getStudent().getTicket().getValue();

        assertEquals(ticketsBefore + 3, ticketsAfter);
        
        // Verificar que o evento foi publicado
        verify(eventPublisher, times(1)).publishEvent(any());
    }

    @Test
    @DisplayName("Should not release 3 more tickets when average grade is below 7")
    public void shouldNotReleaseTicketsWhenAverageGradeIsBelow7() {
        ModuleGrade moduleGrade = new ModuleGrade(UUID.randomUUID(), module, student, enrollment, 5.0);
        enrollment.setModuleGrades(List.of(moduleGrade));

        when(EnrollmentRepository.findByStudentIdAndCourseIdWithGrades(student.getId(), course.getId())).thenReturn(Optional.of(enrollment));

        var ticketsBefore = student.getTicket().getValue();
        var ticketsAfter = finalizeCourseUseCase.execute(student.getId(), course.getId()).getStudent().getTicket().getValue();

        assertEquals(ticketsBefore, ticketsAfter);
        
        // Verificar que o evento NÃO foi publicado quando grade < 7
        verify(eventPublisher, times(0)).publishEvent(any());
    }

    @Test
    @DisplayName("Should throw EnrollmentNotFoundException when enrollment not found")
    void shouldThrowEnrollmentNotFoundExceptionWhenEnrollmentNotFound() {
        assertThrows(EnrollmentNotFoundException.class, () -> finalizeCourseUseCase.execute(student.getId(), course.getId()));
    }
}
