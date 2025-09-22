package com.grupoenzo.aprendizagem_gamificada.bdd.steps;


import com.grupoenzo.aprendizagem_gamificada.bdd.World;
import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.ModuleEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.useCases.FinalizeCourseUseCase;
import com.grupoenzo.aprendizagem_gamificada.modules.student.entities.StudentEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.student.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

final class steps_support {

    private steps_support() {}

    static void student(World world, String name, int tickets) {
        world.student = StudentEntity.builder()
                .id(UUID.randomUUID())
                .name(name)
                .tickets(tickets)
                .build();
    }

    static void courseWithGrade(World world, String courseName, int grade) {
        world.module = ModuleEntity.builder().grade(grade).build();
        world.course = CourseEntity.builder()
                .id(UUID.randomUUID())
                .name(courseName)
                .description("Test course")
                .modules(List.of(world.module))
                .build();

        world.enrollment = EnrollmentEntity.builder()
                .course(world.course)
                .student(world.student)
                .build();

        world.enrollmentRepository = mock(EnrollmentRepository.class);
        when(world.enrollmentRepository.findByStudentIdAndCourseId(
                world.student.getId(),
                world.course.getId()
        )).thenReturn(Optional.of(world.enrollment));

        world.studentRepository = mock(StudentRepository.class);
        when(world.studentRepository.save(world.student)).thenReturn(world.student);

        world.finalizeCourseUseCase = new FinalizeCourseUseCase(world.studentRepository, world.enrollmentRepository);
    }
}