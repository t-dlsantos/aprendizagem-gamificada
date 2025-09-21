package com.grupoenzo.apendizagem_gameficada.exceptions;

import com.grupoenzo.aprendizagem_gamificada.exceptions.EnrollmentNotFound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnrollmentNotFoundTest {

    @Test
    void shouldReturnMessageEnrollmentNotFound() {
        EnrollmentNotFound exception = new EnrollmentNotFound();

        assertEquals("Enrollment not found", exception.getMessage());
    }
}
