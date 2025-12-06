package com.grupoenzo.aprendizagem_gamificada.core.domain.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("CourseFinalizedEvent Tests")
public class CourseFinalizedEventTest {
    
    @Test
    @DisplayName("Should create event with correct student id, course id and grade")
    public void shouldCreateEventWithCorrectData() {
        // Arrange
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        double finalGrade = 8.5;
        Object source = new Object();
        
        // Act
        CourseFinalizedEvent event = new CourseFinalizedEvent(source, studentId, courseId, finalGrade);
        
        // Assert
        assertNotNull(event);
        assertEquals(studentId, event.getStudentId());
        assertEquals(courseId, event.getCourseId());
        assertEquals(finalGrade, event.getFinalGrade());
    }
    
    @Test
    @DisplayName("Should extend ApplicationEvent")
    public void shouldExtendApplicationEvent() {
        // Arrange
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        double finalGrade = 9.0;
        Object source = new Object();
        
        // Act
        CourseFinalizedEvent event = new CourseFinalizedEvent(source, studentId, courseId, finalGrade);
        
        // Assert
        assert event instanceof ApplicationEvent;
    }
    
    @Test
    @DisplayName("Should store event with grade below 7")
    public void shouldStoreEventWithBelowAverageGrade() {
        // Arrange
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        double finalGrade = 5.5;
        Object source = new Object();
        
        // Act
        CourseFinalizedEvent event = new CourseFinalizedEvent(source, studentId, courseId, finalGrade);
        
        // Assert
        assertEquals(finalGrade, event.getFinalGrade());
    }
}
