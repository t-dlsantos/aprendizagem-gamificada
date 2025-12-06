package com.grupoenzo.recommender.presentation.controller;

import com.grupoenzo.recommender.core.domain.Course;
import com.grupoenzo.recommender.core.domain.ExplainedRecommendation;
import com.grupoenzo.recommender.core.domain.RecommendedCourse;
import com.grupoenzo.recommender.core.domain.StudentProfile;
import com.grupoenzo.recommender.core.usecases.GenerateRank;
import com.grupoenzo.recommender.core.usecases.RankExplain;
import com.grupoenzo.recommender.presentation.RecommendationRequest;
import com.grupoenzo.recommender.presentation.RecommendationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class RecommendationControllerTest {

    @Mock
    private GenerateRank generateRank;

    @Mock
    private RankExplain rankExplain;

    private RecommendationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new RecommendationController(generateRank, rankExplain);
    }

    @Test
    void recommend_ShouldReturnOk_WhenSuccessful() {
        UUID studentId = UUID.randomUUID();
        UUID completedCourseId = UUID.randomUUID();
        RecommendationRequest request = new RecommendationRequest();
        request.setStudentId(studentId);
        request.setCompletedCourseId(completedCourseId);

        Course course = new Course(UUID.randomUUID(), "Advanced Java", "Description");
        RecommendedCourse recommendedCourse = new RecommendedCourse(course, 0.9);
        ExplainedRecommendation explainedRecommendation = new ExplainedRecommendation(course, "Because you finished Java Basics", 0.95);

        when(generateRank.execute(eq(studentId), eq(completedCourseId), anyInt()))
                .thenReturn(List.of(recommendedCourse));
        
        when(rankExplain.execute(any(StudentProfile.class), any(List.class)))
                .thenReturn(List.of(explainedRecommendation));

        ResponseEntity<RecommendationResponse> response = controller.recommend(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(course.getId(), response.getBody().getRecommendedCourseId());
        assertEquals("Because you finished Java Basics", response.getBody().getExplanation());
    }
}
