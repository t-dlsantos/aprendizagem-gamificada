package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CourseEntityTest {

    @Test
    void setters_and_modules_work() {
        var course = new Course(UUID.randomUUID(), "DevOps");
        assertEquals("DevOps", course.getName());

        course.setDescription("desc");
        assertEquals("desc", course.getDescription());

        var m1 = new Module(UUID.randomUUID(), "M1", null, course);
        var m2 = new Module(UUID.randomUUID(), "M2", null, course);
        course.setModules(List.of(m1, m2));

        assertEquals(2, course.getModules().size());
    }

    @Test
    void constructor_handles_null_lists() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, "Name", "Description", null, null);
        
        assertNotNull(course.getEnrollments());
        assertTrue(course.getEnrollments().isEmpty());
        assertNotNull(course.getModules());
        assertTrue(course.getModules().isEmpty());
    }

    @Test
    void constructor_handles_provided_lists() {
        UUID id = UUID.randomUUID();
        List<Enrollment> enrollments = new ArrayList<>();
        List<Module> modules = new ArrayList<>();
        Course course = new Course(id, "Name", "Description", enrollments, modules);
        
        assertSame(enrollments, course.getEnrollments());
        assertSame(modules, course.getModules());
    }
}
