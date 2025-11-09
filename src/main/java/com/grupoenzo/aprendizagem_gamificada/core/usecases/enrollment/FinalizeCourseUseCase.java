package com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment;


import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.student.StudentRepository;
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

    public Enrollment execute(UUID idStudent, UUID idCourse) {
        Enrollment enrollment = enrollmentRepository
                .findByStudentIdAndCourseId(idStudent, idCourse)
                .orElseThrow(EnrollmentNotFoundException::new);

        Student student = enrollment.getStudent();

        double averageGrade = enrollment.calculateAverageGrade();

        if (averageGrade >= 7) {
            student.addTickets(3);
            studentRepository.save(student);
        }

        return enrollment;
    }

}