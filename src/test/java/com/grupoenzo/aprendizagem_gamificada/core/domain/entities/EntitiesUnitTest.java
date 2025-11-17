package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EntitiesUnitTest {

    @Test
    void module_setCourse_assignsCourse() {
        Course course = new Course(UUID.randomUUID(), "Course A");
        Module module = new Module(UUID.randomUUID(), "M1", "desc", null);

        assertNull(module.getCourse());
        module.setCourse(course);
        assertSame(course, module.getCourse());
    }

    @Test
    void moduleGrade_setters_updateFields() {
        Course course = new Course(UUID.randomUUID(), "Course A");
        Student student = new Student(UUID.randomUUID(), "S", new Ticket(1));
        Enrollment enrollment = new Enrollment(UUID.randomUUID(), student, course);
        Module module = new Module(UUID.randomUUID(), "M1", "d", course);

        ModuleGrade mg = new ModuleGrade(UUID.randomUUID(), module, student, enrollment, 7.5);

        // change enrollment
        Enrollment newEnrollment = new Enrollment(UUID.randomUUID(), student, course);
        mg.setEnrollment(newEnrollment);
        assertSame(newEnrollment, mg.getEnrollment());

        // change student
        Student newStudent = new Student(UUID.randomUUID(), "S2", new Ticket(2));
        mg.setStudent(newStudent);
        assertSame(newStudent, mg.getStudent());

        // change module
        Module newModule = new Module(UUID.randomUUID(), "M2", "d2", course);
        mg.setModule(newModule);
        assertSame(newModule, mg.getModule());
    }

    @Test
    void course_setters_and_enrollments_work() {
        Course course = new Course(UUID.randomUUID(), "Course X");
        assertEquals("Course X", course.getName());

        course.setName("Course Y");
        assertEquals("Course Y", course.getName());

        List<Enrollment> list = new ArrayList<>();
        Enrollment e = new Enrollment(UUID.randomUUID(), new Student(UUID.randomUUID(), "s", new Ticket(1)), course);
        list.add(e);
        course.setEnrollments(list);
        assertSame(list, course.getEnrollments());
    }

    @Test
    void student_enrollments_getter_setter_and_addTickets() {
        Student student = new Student(UUID.randomUUID(), "Stu", new Ticket(5));
        assertEquals(5, student.getTicket().getValue());

        student.addTickets(3);
        assertEquals(8, student.getTicket().getValue());

        List<Enrollment> enrollments = new ArrayList<>();
        student.setEnrollments(enrollments);
        assertSame(enrollments, student.getEnrollments());
    }

    @Test
    void enrollment_setters_and_getModuleGrades_work() {
        Course course = new Course(UUID.randomUUID(), "C1");
        Student student = new Student(UUID.randomUUID(), "St", new Ticket(2));
        Enrollment enrollment = new Enrollment(UUID.randomUUID(), student, course);

        assertNotNull(enrollment.getModuleGrades());
        assertTrue(enrollment.getModuleGrades().isEmpty());

        // set student
        Student s2 = new Student(UUID.randomUUID(), "S2", new Ticket(1));
        enrollment.setStudent(s2);
        assertSame(s2, enrollment.getStudent());

        // set course
        Course c2 = new Course(UUID.randomUUID(), "C2");
        enrollment.setCourse(c2);
        assertSame(c2, enrollment.getCourse());

        // set module grades
        List<ModuleGrade> mgs = new ArrayList<>();
        enrollment.setModuleGrades(mgs);
        assertSame(mgs, enrollment.getModuleGrades());
    }
}
