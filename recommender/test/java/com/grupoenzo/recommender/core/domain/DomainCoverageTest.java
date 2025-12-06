package com.grupoenzo.recommender.core.domain;

import com.grupoenzo.recommender.core.events.CourseFinalizedEvent;
import com.grupoenzo.recommender.core.exceptions.CourseNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DomainCoverageTest {

    @Test
    void testCourse() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, "Title", "Description");
        assertEquals(id, course.getId());
        assertEquals("Title", course.getTitle());
        assertEquals("Title", course.getName());
        assertEquals("Description", course.getDescription());
        assertEquals("Description", course.getShortDescription());

        Course course2 = new Course(id, "Title2");
        assertEquals("Title2", course2.getTitle());
        assertEquals("", course2.getDescription());
    }

    @Test
    void testEnrollment() {
        Course course = new Course(UUID.randomUUID(), "Title");
        Enrollment enrollment = new Enrollment(course);
        assertEquals(course, enrollment.getCourse());
    }

    @Test
    void testStudentProfile() {
        UUID id = UUID.randomUUID();
        StudentProfile profile = new StudentProfile(id);
        assertEquals(id, profile.getStudentId());
    }

    @Test
    void testRecommendedCourse() {
        Course course = new Course(UUID.randomUUID(), "Title");
        RecommendedCourse rc = new RecommendedCourse(course, 0.8);
        assertEquals(course, rc.getCourse());
        assertEquals(0.8, rc.getSimilarity());
    }

    @Test
    void testExplainedRecommendation() {
        Course course = new Course(UUID.randomUUID(), "Title");
        ExplainedRecommendation er = new ExplainedRecommendation(course, "Explanation", 0.9);
        assertEquals(course, er.getCourse());
        assertEquals("Explanation", er.getExplanation());
        assertEquals(0.9, er.getRelevanceScore());
    }

    @Test
    void testCourseFinalizedEvent() {
        UUID enrollmentId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        Double grade = 9.5;
        CourseFinalizedEvent event = new CourseFinalizedEvent(enrollmentId, studentId, courseId, grade);
        
        assertEquals(enrollmentId, event.enrollmentId());
        assertEquals(studentId, event.studentId());
        assertEquals(courseId, event.courseId());
        assertEquals(grade, event.finalGrade());
    }

    @Test
    void testCourseNotFoundException() {
        CourseNotFoundException ex = new CourseNotFoundException();
        assertNotNull(ex);
        
        CourseNotFoundException ex2 = new CourseNotFoundException("Message");
        assertEquals("Message", ex2.getMessage());

        CourseNotFoundException ex3 = new CourseNotFoundException("Message", new RuntimeException());
        assertEquals("Message", ex3.getMessage());
        assertNotNull(ex3.getCause());
    }
}
