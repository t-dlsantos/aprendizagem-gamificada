package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DomainEntitiesCoverageTest {

    @Test
    void module_setCourse_null_and_nonNull() {
        Course course = new Course(UUID.randomUUID(), "C");
        Module module = new Module(UUID.randomUUID(), "M", "d", course);

        // set to null
        module.setCourse(null);
        assertNull(module.getCourse());

        // set back
        module.setCourse(course);
        assertSame(course, module.getCourse());
    }

    @Test
    void moduleGrade_setters_accept_nulls_and_values() {
        Course course = new Course(UUID.randomUUID(), "C");
        Student student = new Student(UUID.randomUUID(), "S", new Ticket(1));
        Enrollment enrollment = new Enrollment(UUID.randomUUID(), student, course);
        Module module = new Module(UUID.randomUUID(), "M", "d", course);

        ModuleGrade mg = new ModuleGrade(UUID.randomUUID(), module, student, enrollment, 5.0);

        mg.setEnrollment(null);
        assertNull(mg.getEnrollment());

        mg.setStudent(null);
        assertNull(mg.getStudent());

        mg.setModule(null);
        assertNull(mg.getModule());

        // restore
        mg.setEnrollment(enrollment);
        mg.setStudent(student);
        mg.setModule(module);

        assertSame(enrollment, mg.getEnrollment());
        assertSame(student, mg.getStudent());
        assertSame(module, mg.getModule());
    }

    @Test
    void course_enrollments_and_name_setters() {
        Course c = new Course(UUID.randomUUID(), "N");
        assertEquals("N", c.getName());

        c.setName("NN");
        assertEquals("NN", c.getName());

        // enrollments null
        c.setEnrollments(null);
        assertNull(c.getEnrollments());

        List<Enrollment> ens = new ArrayList<>();
        c.setEnrollments(ens);
        assertSame(ens, c.getEnrollments());
    }

    @Test
    void student_enrollments_getters_setters_variants() {
        Student s = new Student(UUID.randomUUID(), "Stu", new Ticket(2));
        assertNotNull(s.getTicket());

        s.setEnrollments(null);
        assertNull(s.getEnrollments());

        List<Enrollment> list = new ArrayList<>();
        s.setEnrollments(list);
        assertSame(list, s.getEnrollments());
    }

    @Test
    void enrollment_setters_modules_variants() {
        Course c = new Course(UUID.randomUUID(), "C");
        Student s = new Student(UUID.randomUUID(), "S", new Ticket(1));
        Enrollment en = new Enrollment(UUID.randomUUID(), s, c);

        en.setStudent(null);
        assertNull(en.getStudent());

        en.setCourse(null);
        assertNull(en.getCourse());

        en.setModuleGrades(null);
        assertNull(en.getModuleGrades());

        List<ModuleGrade> mgs = new ArrayList<>();
        en.setModuleGrades(mgs);
        assertSame(mgs, en.getModuleGrades());
    }
}
