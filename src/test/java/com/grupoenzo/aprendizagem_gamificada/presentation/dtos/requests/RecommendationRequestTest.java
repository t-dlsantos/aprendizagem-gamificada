package com.grupoenzo.aprendizagem_gamificada.presentation.dtos.requests;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class RecommendationRequestTest {

    @Test
    void testConstructorAndGetters() {
        UUID studentId = UUID.randomUUID();
        UUID completedCourseId = UUID.randomUUID();
        List<UUID> enrolledCourseIds = List.of(UUID.randomUUID());
        String completedCourseName = "Java Course";

        RecommendationRequest request = new RecommendationRequest(studentId, completedCourseId, enrolledCourseIds, completedCourseName);

        assertEquals(studentId, request.getStudentId());
        assertEquals(completedCourseId, request.getCompletedCourseId());
        assertEquals(enrolledCourseIds, request.getEnrolledCourseIds());
        assertEquals(completedCourseName, request.getCompletedCourseName());
    }

    @Test
    void testNoArgsConstructor() {
        RecommendationRequest request = new RecommendationRequest();
        assertNotNull(request);
    }
}
