package com.grupoenzo.aprendizagem_gamificada.core.useCases.enrollment;

import com.grupoenzo.aprendizagem_gamificada.exceptions.EnrollmentNotFoundException;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.StudentEntity;
import com.grupoenzo.aprendizagem_gamificada.core.useCases.student.StudentRepository;
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
                .orElseThrow(EnrollmentNotFoundException::new);

        StudentEntity student = enrollment.getStudent();

        double averageGrade = enrollment.calculateAverageGrade();

        if (averageGrade >= 7) {
            student.addTickets(3);
            studentRepository.save(student);
        }

        return enrollment;
    }

}