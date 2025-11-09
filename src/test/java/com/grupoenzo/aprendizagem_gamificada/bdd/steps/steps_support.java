package com.grupoenzo.aprendizagem_gamificada.bdd.steps;


import com.grupoenzo.aprendizagem_gamificada.bdd.World;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.CourseEntity;
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
        world.student = Student.builder()
                .id(UUID.randomUUID())
                .name(name)
                .tickets(tickets)
                .build();
    }

    static void courseWithGrade(World world, String courseName, double grade) {
        world.course = CourseEntity.builder()
                .id(UUID.randomUUID())
                .name(courseName)
                .description("Test course")
                .build();

        world.module = Module.builder()
                .id(UUID.randomUUID())
                .name("Module 1")
                .description("Test module")
                .course(world.course)
                .build();
                
        world.course.setModules(List.of(world.module));

        world.enrollment = Enrollment.builder()
                .id(UUID.randomUUID())
                .course(world.course)
                .student(world.student)
                .build();

        world.moduleGrade = ModuleGrade.builder()
                .student(world.student)
                .module(world.module)
                .enrollment(world.enrollment)
                .grade(grade)
                .build();
        
        world.enrollment.setModuleGrades(List.of(world.moduleGrade));

        world.EnrollmentRepository = mock(EnrollmentRepository.class);
        when(world.EnrollmentRepository.findByStudentIdAndCourseId(
                world.student.getId(),
                world.course.getId()
        )).thenReturn(Optional.of(world.enrollment));

        world.StudentRepository = mock(StudentRepository.class);
        when(world.StudentRepository.save(world.student)).thenReturn(world.student);

        world.finalizeCourseUseCase = new FinalizeCourseUseCase(world.StudentRepository, world.EnrollmentRepository);
    }
}