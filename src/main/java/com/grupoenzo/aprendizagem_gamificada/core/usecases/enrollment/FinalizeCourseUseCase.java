package com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment;

import org.springframework.stereotype.Service;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.domain.enums.EnrollmentStatus;
import com.grupoenzo.aprendizagem_gamificada.core.exceptions.InsufficientGradeException;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.student.repositories.StudentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.exceptions.EnrollmentNotFoundException;

import java.util.UUID;

@Service
public class FinalizeCourseUseCase {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public FinalizeCourseUseCase(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Enrollment execute(UUID idEnrollment) {
        Enrollment enrollment = enrollmentRepository.findByIdWithGrades(idEnrollment).orElseThrow(EnrollmentNotFoundException::new);

        Student student = enrollment.getStudent();

        double averageGrade = enrollment.calculateAverageGrade();

        if (averageGrade < 7)
            throw new InsufficientGradeException();

        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        student.addTickets(3);
        studentRepository.save(student);

        return enrollment;
    }

    // TODO: validar permanência
    public Enrollment execute(UUID idStudent, UUID idCourse) {
        Enrollment enrollment = enrollmentRepository
                .findByStudentIdAndCourseIdWithGrades(idStudent, idCourse)
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