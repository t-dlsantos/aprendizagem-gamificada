package com.grupoenzo.recommender.infra.messaging;

import com.grupoenzo.recommender.core.domain.Course;
import com.grupoenzo.recommender.core.domain.ExplainedRecommendation;
import com.grupoenzo.recommender.core.domain.RecommendedCourse;
import com.grupoenzo.recommender.core.domain.StudentProfile;
import com.grupoenzo.recommender.core.events.CourseFinalizedEvent;
import com.grupoenzo.recommender.core.usecases.GenerateRank;
import com.grupoenzo.recommender.core.usecases.RankExplain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CourseFinalizedListenerTest {

    @Mock
    private GenerateRank generateRank;

    @Mock
    private RankExplain rankExplain;

    private CourseFinalizedListener listener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listener = new CourseFinalizedListener(generateRank, rankExplain);
    }

    @Test
    void handleCourseFinalized_ShouldProcessEvent() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        CourseFinalizedEvent event = new CourseFinalizedEvent(UUID.randomUUID(), studentId, courseId, 10.0);

        Course course = new Course(UUID.randomUUID(), "Course");
        RecommendedCourse rc = new RecommendedCourse(course, 0.9);
        ExplainedRecommendation er = new ExplainedRecommendation(course, "Exp", 0.9);

        when(generateRank.execute(eq(studentId), eq(courseId), anyInt())).thenReturn(List.of(rc));
        when(rankExplain.execute(any(StudentProfile.class), anyList())).thenReturn(List.of(er));

        listener.handleCourseFinalized(event);

        verify(generateRank).execute(eq(studentId), eq(courseId), eq(5));
        verify(rankExplain).execute(any(StudentProfile.class), anyList());
    }

    @Test
    void handleCourseFinalized_ShouldHandleEmptyRank() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        CourseFinalizedEvent event = new CourseFinalizedEvent(UUID.randomUUID(), studentId, courseId, 10.0);

        when(generateRank.execute(any(), any(), anyInt())).thenReturn(Collections.emptyList());

        listener.handleCourseFinalized(event);

        verify(generateRank).execute(eq(studentId), eq(courseId), eq(5));
        verify(rankExplain, never()).execute(any(), any());
    }

    @Test
    void handleCourseFinalized_ShouldHandleException() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        CourseFinalizedEvent event = new CourseFinalizedEvent(UUID.randomUUID(), studentId, courseId, 10.0);

        when(generateRank.execute(any(), any(), anyInt())).thenThrow(new RuntimeException("Error"));

        // Should not throw
        listener.handleCourseFinalized(event);
    }
}
