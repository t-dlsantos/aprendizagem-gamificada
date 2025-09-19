package com.grupoenzo.aprendizagem_gamificada.exceptions;

public class EnrollmentNotFound extends RuntimeException {
    public EnrollmentNotFound() {
        super("Enrollment not found");
    }
}