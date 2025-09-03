package com.grupoenzo.aprendizagem_gamificada;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.grupoenzo.domain.Course;
import com.grupoenzo.domain.Student;

public class CoursesReleasedTest {

  private Student student;
  private Course course;

  @BeforeEach
  public void setup() {
    student = new Student("Thiago");
    course = new Course("Matemática");
  }

  @Test
  public void testReleaseCoursesWithMediaEqual7() {
    course.finalize(7.0, student);
    assertEquals(3, student.getAvailableCourses()); 
  }

  @Test
  public void testReleaseCoursesWithMediaGreaterThan7() {
    course.finalize(8.5, student);
    assertEquals(3, student.getAvailableCourses());
  }

  @Test
  public void testDoNotReleaseCoursesWhenMediaLessThan7() {
    course.finalize(6.5, student);
    assertEquals(0, student.getAvailableCourses());
  }
}