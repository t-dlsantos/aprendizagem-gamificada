package com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    @Test
    void isValidReturnsTrueWhenValueGreaterThanZero() {
        Ticket t = new Ticket(5);
        assertTrue(t.isValid());
        assertEquals(5, t.getValue());
    }

    @Test
    void isValidReturnsFalseWhenZeroOrNegative() {
        Ticket t0 = new Ticket(0);
        Ticket tNeg = new Ticket(-1);

        assertFalse(t0.isValid());
        assertFalse(tNeg.isValid());
    }
}
