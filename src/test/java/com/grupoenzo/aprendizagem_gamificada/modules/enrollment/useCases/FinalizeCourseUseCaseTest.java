package com.grupoenzo.aprendizagem_gamificada.modules.enrollment.useCases;

import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.ModuleEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.course.repositories.CourseRepository;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.modules.student.entities.StudentEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.student.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FinalizeCourseUseCaseTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private EnrollmentRepository enrollmentRepository;
    @InjectMocks
    private FinalizeCourseUseCase finalizeCourseUseCase;

    private StudentEntity student;
    private CourseEntity course;
    private ModuleEntity module;
    private EnrollmentEntity enrollment;

    @BeforeEach
    public void setup() {
        this.student = StudentEntity.builder().id(UUID.randomUUID()).name("Thiago").tickets(0).build();
        this.module = ModuleEntity.builder().build();
        this.course = CourseEntity.builder().id(UUID.randomUUID()).name("DevOps").description("A course to master DevOps").build();
        this.enrollment = EnrollmentEntity.builder().course(course).student(student).build();
    }

    @Test
    @DisplayName("Should release 3 more tickets when average grade equals to 7")
    public void shouldReleaseTicketsWhenAverageGradeIs7() {
        module.setGrade(7);
        course.setModules(List.of(module));

        when(enrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId())).thenReturn(Optional.of(enrollment));

        var ticketsBefore = student.getTickets();
        var ticketsAfter = finalizeCourseUseCase.execute(student.getId(), course.getId()).getStudent().getTickets();

        assertEquals(ticketsBefore + 3, ticketsAfter);
    }

    @Test
    @DisplayName("Should release 3 more tickets when average grande is above 7")
    public void shouldReleaseTicketsWhenAverageGradeIsAbove7() {
        module.setGrade(8);
        course.setModules(List.of(module));

        when(enrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId())).thenReturn(Optional.of(enrollment));

        var ticketsBefore = student.getTickets();
        var ticketsAfter = finalizeCourseUseCase.execute(student.getId(), course.getId()).getStudent().getTickets();

        assertEquals(ticketsBefore + 3, ticketsAfter);
    }

    @Test
    @DisplayName("Should not release 3 more tickets when average grade is below 7")
    public void shouldNotReleaseTicketsWhenAverageGradeIsBelow7() {
        module.setGrade(5);
        course.setModules(List.of(module));

        when(enrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId())).thenReturn(Optional.of(enrollment));

        var ticketsBefore = student.getTickets();
        var ticketsAfter = finalizeCourseUseCase.execute(student.getId(), course.getId()).getStudent().getTickets();

        assertEquals(ticketsBefore, ticketsAfter);
    }

}