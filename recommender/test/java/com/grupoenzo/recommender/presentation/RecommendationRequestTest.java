package com.grupoenzo.recommender.presentation;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class RecommendationRequestTest {

    @Test
    void testGettersAndSetters() {
        RecommendationRequest request = new RecommendationRequest();
        UUID studentId = UUID.randomUUID();
        UUID completedCourseId = UUID.randomUUID();
        List<UUID> enrolledCourseIds = List.of(UUID.randomUUID());
        String completedCourseName = "Java Basics";

        request.setStudentId(studentId);
        request.setCompletedCourseId(completedCourseId);
        request.setEnrolledCourseIds(enrolledCourseIds);
        request.setCompletedCourseName(completedCourseName);

        assertEquals(studentId, request.getStudentId());
        assertEquals(completedCourseId, request.getCompletedCourseId());
        assertEquals(enrolledCourseIds, request.getEnrolledCourseIds());
        assertEquals(completedCourseName, request.getCompletedCourseName());
    }
}
