package com.grupoenzo.aprendizagem_gamificada.bdd.steps;


import com.grupoenzo.aprendizagem_gamificada.bdd.World;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Module;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.FinalizeCourseUseCase;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.student.repositories.StudentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

final class steps_support {

    private steps_support() {}

    static void student(World world, String name, int tickets) {
        world.student = new Student(UUID.randomUUID(), name, new com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket(tickets));
    }

    static void courseWithGrade(World world, String courseName, double grade) {
        world.course = new Course(UUID.randomUUID(), courseName);
        world.course.setDescription("Test course");

        world.module = new Module(UUID.randomUUID(), "Module 1", "Test module", world.course);
                
        world.course.setModules(List.of(world.module));

        world.enrollment = new Enrollment(UUID.randomUUID(), world.student, world.course);

        world.moduleGrade = new ModuleGrade(UUID.randomUUID(), world.module, world.student, world.enrollment, grade);
        
        world.enrollment.setModuleGrades(List.of(world.moduleGrade));

        world.EnrollmentRepository = mock(EnrollmentRepository.class);
        when(world.EnrollmentRepository.findByStudentIdAndCourseId(
                world.student.getId(),
                world.course.getId()
        )).thenReturn(Optional.of(world.enrollment));

        world.StudentRepository = mock(StudentRepository.class);
        when(world.StudentRepository.save(world.student)).thenReturn(world.student);

        world.finalizeCourseUseCase = new FinalizeCourseUseCase(world.StudentRepository, world.EnrollmentRepository, world.eventPublisher);
    }
}