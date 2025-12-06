package com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment;

import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.domain.enums.EnrollmentStatus;
import com.grupoenzo.aprendizagem_gamificada.core.domain.events.CourseFinalizedEvent;
import com.grupoenzo.aprendizagem_gamificada.core.exceptions.InsufficientGradeException;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.student.repositories.StudentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.exceptions.EnrollmentNotFoundException;

import java.util.UUID;

@Service
public class FinalizeCourseUseCase {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public FinalizeCourseUseCase(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository, ApplicationEventPublisher eventPublisher) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.eventPublisher = eventPublisher;
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

        // Publicar evento para iniciar processo de recomendação
        eventPublisher.publishEvent(new CourseFinalizedEvent(
            this,
            student.getId(),
            enrollment.getCourse().getId(),
            averageGrade
        ));

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
            
            // Publicar evento para iniciar processo de recomendação
            eventPublisher.publishEvent(new CourseFinalizedEvent(
                this,
                student.getId(),
                enrollment.getCourse().getId(),
                averageGrade
            ));
        }

        return enrollment;
    }
}