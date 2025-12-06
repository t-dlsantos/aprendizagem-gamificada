package com.grupoenzo.recommender.infra.listeners;

import com.grupoenzo.aprendizagem_gamificada.core.domain.events.CourseFinalizedEvent;
import com.grupoenzo.recommender.core.domain.StudentProfile;
import com.grupoenzo.recommender.core.domain.RecommendedCourse;
import com.grupoenzo.recommender.core.domain.Course;
import com.grupoenzo.recommender.core.usecases.GenerateRank;
import com.grupoenzo.recommender.core.usecases.RankExplain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseRecommendationListenerTest {
    
    @Mock
    private GenerateRank generateRank;
    
    @Mock
    private RankExplain rankExplain;
    
    @InjectMocks
    private CourseRecommendationListener listener;
    
    private UUID studentId;
    private UUID courseId;
    private CourseFinalizedEvent event;
    
    @BeforeEach
    public void setup() {
        studentId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        event = new CourseFinalizedEvent(this, studentId, courseId, 8.5);
    }
    
    @Test
    @DisplayName("Should process course recommendation when event is received")
    public void shouldProcessRecommendationWhenEventReceived() {
        // Arrange
        var candidates = List.of(
            new RecommendedCourse(new Course(UUID.randomUUID(), "Spring Advanced"), 0.95)
        );
        
        var explanations = List.of(
            new com.grupoenzo.recommender.core.domain.ExplainedRecommendation(
                candidates.get(0).getCourse(), 
                "Because you liked Java...",
                candidates.get(0).getSimilarity()
            )
        );
        
        when(generateRank.execute(studentId, courseId, 5))
            .thenReturn(candidates);
        
        when(rankExplain.execute(any(StudentProfile.class), any()))
            .thenReturn(explanations);
        
        // Act
        listener.onCourseFinalizedEvent(event);
        
        // Assert
        verify(generateRank, times(1)).execute(studentId, courseId, 5);
        verify(rankExplain, times(1)).execute(any(StudentProfile.class), any());
    }
    
    @Test
    @DisplayName("Should handle exception gracefully when recommendation fails")
    public void shouldHandleExceptionGracefully() {
        // Arrange
        when(generateRank.execute(studentId, courseId, 5))
            .thenThrow(new RuntimeException("RAG error"));
        
        // Act & Assert - should not throw exception
        listener.onCourseFinalizedEvent(event);
        
        verify(generateRank, times(1)).execute(studentId, courseId, 5);
    }

    @Test
    @DisplayName("Should handle empty recommendations")
    public void shouldHandleEmptyRecommendations() {
        // Arrange
        when(generateRank.execute(studentId, courseId, 5))
            .thenReturn(List.of());
        
        when(rankExplain.execute(any(StudentProfile.class), any()))
            .thenReturn(List.of());
        
        // Act
        listener.onCourseFinalizedEvent(event);
        
        // Assert
        verify(generateRank, times(1)).execute(studentId, courseId, 5);
        verify(rankExplain, times(1)).execute(any(StudentProfile.class), any());
    }
}
