package com.grupoenzo.aprendizagem_gamificada.presentation.controller.enrollment;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Module;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket;
import com.grupoenzo.aprendizagem_gamificada.core.domain.enums.EnrollmentStatus;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.FinalizeCourseUseCase;
import com.grupoenzo.aprendizagem_gamificada.infra.http.RecommenderClient;
import com.grupoenzo.aprendizagem_gamificada.presentation.dtos.requests.RecommendationRequest;
import com.grupoenzo.aprendizagem_gamificada.presentation.dtos.responses.FinalizeCourseResponse;
import com.grupoenzo.aprendizagem_gamificada.presentation.dtos.responses.RecommendationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EnrollmentControllerTest {

    @Mock
    private FinalizeCourseUseCase finalizeCourseUseCase;

    @Mock
    private RecommenderClient recommenderClient;

    private EnrollmentController enrollmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        enrollmentController = new EnrollmentController(finalizeCourseUseCase, recommenderClient);
    }

    @Test
    void finalize_ShouldReturnOk_WhenSuccessful() {
        UUID enrollmentId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        Student student = new Student(studentId, "John Doe", new Ticket(0));
        Course course = new Course(courseId, "Java Course");
        
        Enrollment enrollment = mock(Enrollment.class);
        when(enrollment.getId()).thenReturn(enrollmentId);
        when(enrollment.getStudent()).thenReturn(student);
        when(enrollment.getCourse()).thenReturn(course);
        when(enrollment.getStatus()).thenReturn(EnrollmentStatus.COMPLETED);
        when(enrollment.calculateAverageGrade()).thenReturn(9.5);
        
        when(finalizeCourseUseCase.execute(enrollmentId)).thenReturn(enrollment);
        
        RecommendationResponse recommendationResponse = new RecommendationResponse();
        recommendationResponse.setCourseName("Advanced Java");
        when(recommenderClient.getRecommendation(any(RecommendationRequest.class))).thenReturn(recommendationResponse);

        ResponseEntity<Object> response = enrollmentController.finalize(enrollmentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof FinalizeCourseResponse);
        FinalizeCourseResponse body = (FinalizeCourseResponse) response.getBody();
        assertEquals(enrollmentId, body.getEnrollmentId());
        assertEquals(recommendationResponse, body.getRecommendation());
        
        verify(finalizeCourseUseCase).execute(enrollmentId);
        verify(recommenderClient).getRecommendation(any(RecommendationRequest.class));
    }

    @Test
    void finalize_ShouldReturnOk_WhenSuccessful_WithExistingEnrollments() {
        UUID enrollmentId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID otherCourseId = UUID.randomUUID();

        Student student = new Student(studentId, "John Doe", new Ticket(0));
        Course course = new Course(courseId, "Java Course");
        
        Enrollment enrollment = mock(Enrollment.class);
        when(enrollment.getId()).thenReturn(enrollmentId);
        when(enrollment.getStudent()).thenReturn(student);
        when(enrollment.getCourse()).thenReturn(course);
        when(enrollment.getStatus()).thenReturn(EnrollmentStatus.COMPLETED);
        when(enrollment.calculateAverageGrade()).thenReturn(9.5);
        
        // Add another enrollment to trigger the lambda
        Course otherCourse = new Course(otherCourseId, "Python Course");
        Enrollment otherEnrollment = new Enrollment(UUID.randomUUID(), student, otherCourse);
        student.getEnrollments().add(otherEnrollment);
        
        when(finalizeCourseUseCase.execute(enrollmentId)).thenReturn(enrollment);
        
        RecommendationResponse recommendationResponse = new RecommendationResponse();
        recommendationResponse.setCourseName("Advanced Java");
        when(recommenderClient.getRecommendation(any(RecommendationRequest.class))).thenReturn(recommendationResponse);

        ResponseEntity<Object> response = enrollmentController.finalize(enrollmentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Verify that the recommendation request contains the other course ID
        verify(recommenderClient).getRecommendation(argThat(req -> 
            req.getEnrolledCourseIds().contains(otherCourseId)
        ));
    }

    @Test
    void finalize_ShouldReturnBadRequest_WhenExceptionOccurs() {
        UUID enrollmentId = UUID.randomUUID();
        String errorMessage = "Enrollment not found";
        
        when(finalizeCourseUseCase.execute(enrollmentId)).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<Object> response = enrollmentController.finalize(enrollmentId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
        
        verify(finalizeCourseUseCase).execute(enrollmentId);
        verifyNoInteractions(recommenderClient);
    }

    @Test
    void finalize_ShouldHandleNullEnrollmentsList() {
        UUID enrollmentId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        Student student = new Student(studentId, "John Doe", new Ticket(0));
        // Force enrollments to be null if possible, or just ensure the getter returns null if that's the logic
        // Since Student initializes enrollments to new ArrayList(), we might need to use reflection or a mock if we want to test the null check specifically.
        // However, looking at the code: enrollment.getStudent().getEnrollments() != null
        // If Student always has a list, this check is redundant but covered if we can make it null.
        // Let's try to mock the student to return null for enrollments.
        
        Enrollment enrollment = mock(Enrollment.class);
        Student mockStudent = mock(Student.class);
        Course mockCourse = mock(Course.class);
        
        when(enrollment.getId()).thenReturn(enrollmentId);
        when(enrollment.getStudent()).thenReturn(mockStudent);
        when(enrollment.getCourse()).thenReturn(mockCourse);
        when(enrollment.getStatus()).thenReturn(EnrollmentStatus.COMPLETED);
        when(enrollment.calculateAverageGrade()).thenReturn(9.5);
        
        when(mockStudent.getId()).thenReturn(studentId);
        when(mockStudent.getEnrollments()).thenReturn(null); // This triggers the else branch
        
        when(mockCourse.getId()).thenReturn(courseId);
        when(mockCourse.getName()).thenReturn("Java Course");

        when(finalizeCourseUseCase.execute(enrollmentId)).thenReturn(enrollment);
        
        RecommendationResponse recommendationResponse = new RecommendationResponse();
        when(recommenderClient.getRecommendation(any(RecommendationRequest.class))).thenReturn(recommendationResponse);

        ResponseEntity<Object> response = enrollmentController.finalize(enrollmentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(recommenderClient).getRecommendation(argThat(req -> req.getEnrolledCourseIds().isEmpty()));
    }
}
