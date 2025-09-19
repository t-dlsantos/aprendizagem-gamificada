package com.grupoenzo.aprendizagem_gamificada.modules.enrollment.useCases;

import com.grupoenzo.aprendizagem_gamificada.exceptions.EnrollmentNotFound;
import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.modules.student.entities.StudentEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.student.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FinalizeCourseUseCase {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public FinalizeCourseUseCase(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public EnrollmentEntity execute(UUID idStudent, UUID idCourse) {
        EnrollmentEntity enrollment = enrollmentRepository
                .findByStudentIdAndCourseId(idStudent, idCourse)
                .orElseThrow(EnrollmentNotFound::new);

        StudentEntity student = enrollment.getStudent();
        CourseEntity course = enrollment.getCourse();

        double averageGrade = course.calculateAverageGrade();

        if (averageGrade >= 7) {
            student.addTickets(3);
            studentRepository.save(student);
        }

        return enrollment;
    }

}