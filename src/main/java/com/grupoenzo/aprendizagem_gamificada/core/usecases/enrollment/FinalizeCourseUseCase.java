package com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment;


import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.domain.enums.EnrollmentStatus;
import com.grupoenzo.aprendizagem_gamificada.core.exceptions.InsufficientGradeException;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.student.repositories.StudentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.exceptions.EnrollmentNotFoundException;

import java.util.UUID;

public class FinalizeCourseUseCase {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public FinalizeCourseUseCase(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Enrollment execute(UUID idEnrollment) {
        Enrollment enrollment = enrollmentRepository.findById(idEnrollment).orElseThrow(EnrollmentNotFoundException::new);
        Student student = enrollment.getStudent();

        double averageGrade = enrollment.calculateAverageGrade();

        if (averageGrade < 7)
            throw new InsufficientGradeException();

        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        student.addTickets(3);
        studentRepository.save(student);
        System.out.println("Passamos pela guerra!" + enrollment);

        return enrollment;
    }

    // TODO: validar permanência
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