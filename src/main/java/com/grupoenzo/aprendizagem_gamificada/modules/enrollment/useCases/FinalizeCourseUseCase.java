package com.grupoenzo.aprendizagem_gamificada.modules.enrollment.useCases;

import com.grupoenzo.aprendizagem_gamificada.modules.course.repositories.CourseRepository;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.modules.student.entities.StudentEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.student.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FinalizeCourseUseCase {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public FinalizeCourseUseCase(CourseRepository courseRepository, StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public EnrollmentEntity execute(UUID idStudent, UUID idCourse) {
        return enrollmentRepository
                .findByStudentIdAndCourseId(idStudent, idCourse)
                // TODO: improve handling of exception with a specified class
                .orElseThrow(RuntimeException::new);
    }

}