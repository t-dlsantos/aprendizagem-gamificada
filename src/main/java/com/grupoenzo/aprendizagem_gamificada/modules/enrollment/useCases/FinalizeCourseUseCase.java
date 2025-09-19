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
        EnrollmentEntity enrollment = enrollmentRepository
                .findByStudentIdAndCourseId(idStudent, idCourse)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        StudentEntity student = enrollment.getStudent();
        student.setTickets(student.getTickets() - 1);

        studentRepository.save(student);
        return enrollment;
    }

}