package com.grupoenzo.recommender.core.usecases;

import com.grupoenzo.recommender.core.domain.Course;
import com.grupoenzo.recommender.core.domain.Enrollment;
import com.grupoenzo.recommender.core.domain.RecommendedCourse;
import com.grupoenzo.recommender.core.embeddings.EmbeddingProvider;
import com.grupoenzo.recommender.core.exceptions.CourseNotFoundException;
import com.grupoenzo.recommender.core.ports.repositories.CourseRepository;
import com.grupoenzo.recommender.core.ports.repositories.EnrollmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GenerateRankTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private EmbeddingProvider embeddingProvider;

    private GenerateRank generateRank;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        generateRank = new GenerateRank(courseRepository, enrollmentRepository, embeddingProvider);
    }

    @Test
    void execute_ShouldReturnRankedCourses() {
        UUID studentId = UUID.randomUUID();
        UUID completedCourseId = UUID.randomUUID();
        UUID candidateCourseId = UUID.randomUUID();

        Course completedCourse = new Course(completedCourseId, "Java Basics", "Intro to Java");
        Course candidateCourse = new Course(candidateCourseId, "Advanced Java", "Deep dive");

        when(enrollmentRepository.findByStudentId(studentId)).thenReturn(Collections.emptyList());
        when(courseRepository.findById(completedCourseId)).thenReturn(Optional.of(completedCourse));
        when(courseRepository.findAll()).thenReturn(List.of(completedCourse, candidateCourse));
        
        when(embeddingProvider.embed(anyString())).thenReturn(new float[]{1.0f, 0.0f});

        List<RecommendedCourse> result = generateRank.execute(studentId, completedCourseId, 5);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(candidateCourseId, result.get(0).getCourse().getId());
    }

    @Test
    void execute_ShouldThrowException_WhenCompletedCourseNotFound() {
        UUID studentId = UUID.randomUUID();
        UUID completedCourseId = UUID.randomUUID();

        when(enrollmentRepository.findByStudentId(studentId)).thenReturn(Collections.emptyList());
        when(courseRepository.findById(completedCourseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> 
            generateRank.execute(studentId, completedCourseId, 5)
        );
    }

    @Test
    void execute_ShouldFilterEnrolledCourses() {
        UUID studentId = UUID.randomUUID();
        UUID completedCourseId = UUID.randomUUID();
        UUID enrolledCourseId = UUID.randomUUID();
        UUID newCourseId = UUID.randomUUID();

        Course completedCourse = new Course(completedCourseId, "Completed", "Desc");
        Course enrolledCourse = new Course(enrolledCourseId, "Enrolled", "Desc");
        Course newCourse = new Course(newCourseId, "New", "Desc");

        when(courseRepository.findById(completedCourseId)).thenReturn(Optional.of(completedCourse));
        when(courseRepository.findAll()).thenReturn(List.of(completedCourse, enrolledCourse, newCourse));
        
        Enrollment enrollment = new Enrollment(enrolledCourse);
        when(enrollmentRepository.findByStudentId(studentId)).thenReturn(List.of(enrollment));
        
        when(embeddingProvider.embed(anyString())).thenReturn(new float[]{1.0f});

        List<RecommendedCourse> result = generateRank.execute(studentId, completedCourseId, 5);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(newCourseId, result.get(0).getCourse().getId());
    }
}
