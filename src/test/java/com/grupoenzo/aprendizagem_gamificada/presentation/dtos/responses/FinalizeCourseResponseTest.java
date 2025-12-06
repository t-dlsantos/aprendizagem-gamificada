package com.grupoenzo.aprendizagem_gamificada.presentation.dtos.responses;

import com.grupoenzo.aprendizagem_gamificada.core.domain.enums.EnrollmentStatus;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class FinalizeCourseResponseTest {

    @Test
    void testConstructorAndGetters() {
        UUID enrollmentId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        EnrollmentStatus status = EnrollmentStatus.COMPLETED;
        Double averageGrade = 9.5;
        Integer ticketsAdded = 10;

        FinalizeCourseResponse response = new FinalizeCourseResponse(enrollmentId, studentId, courseId, status, averageGrade, ticketsAdded);

        assertEquals(enrollmentId, response.getEnrollmentId());
        assertEquals(studentId, response.getStudentId());
        assertEquals(courseId, response.getCourseId());
        assertEquals(status, response.getStatus());
        assertEquals(averageGrade, response.getAverageGrade());
        assertEquals(ticketsAdded, response.getTicketsAdded());
    }

    @Test
    void testSetRecommendation() {
        UUID enrollmentId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        EnrollmentStatus status = EnrollmentStatus.COMPLETED;
        Double averageGrade = 9.5;
        Integer ticketsAdded = 10;

        FinalizeCourseResponse response = new FinalizeCourseResponse(enrollmentId, studentId, courseId, status, averageGrade, ticketsAdded);
        RecommendationResponse recommendation = new RecommendationResponse();
        response.setRecommendation(recommendation);

        assertEquals(recommendation, response.getRecommendation());
    }
}
