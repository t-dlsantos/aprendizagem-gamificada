package com.grupoenzo.aprendizagem_gamificada.exceptions;

public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException() {
        super("Enrollment not found");
    }
}