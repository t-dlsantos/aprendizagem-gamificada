package com.grupoenzo.aprendizagem_gamificada.core.domain.exceptions;

public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException() {
        super("Enrollment not found");
    }
}