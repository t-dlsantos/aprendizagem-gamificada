package com.grupoenzo.aprendizagem_gamificada.core.exceptions;

public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException() {
        super("Enrollment not found");
    }
}