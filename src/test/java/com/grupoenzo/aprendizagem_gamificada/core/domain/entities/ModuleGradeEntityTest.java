package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ModuleGradeEntityTest {

    @Test
    void constructor_and_getters_setters_work() {
        var id = UUID.randomUUID();
        var module = new Module(UUID.randomUUID(), "M1", null, null);
        var student = new Student(UUID.randomUUID(), "S1", new com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket(0));
        var enrollment = new Enrollment(UUID.randomUUID(), student, null);

        var mg = new ModuleGrade(id, module, student, enrollment, 7.5);
        assertEquals(7.5, mg.getGrade());
        assertNotNull(mg.getCompletedAt());

        mg.setGrade(8.0);
        assertEquals(8.0, mg.getGrade());

        var now = LocalDateTime.now();
        mg.setCompletedAt(now);
        assertEquals(now, mg.getCompletedAt());
    }
}
