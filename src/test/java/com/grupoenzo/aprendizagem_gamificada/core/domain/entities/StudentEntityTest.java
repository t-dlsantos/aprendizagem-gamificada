package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class StudentEntityTest {

    @Test
    void addTickets_and_setters_work() {
        var student = new Student(UUID.randomUUID(), "Ana", new Ticket(1));
        assertEquals(1, student.getTicket().getValue());

        student.addTickets(3);
        assertEquals(4, student.getTicket().getValue());

        student.setName("Maria");
        assertEquals("Maria", student.getName());
    }
}
