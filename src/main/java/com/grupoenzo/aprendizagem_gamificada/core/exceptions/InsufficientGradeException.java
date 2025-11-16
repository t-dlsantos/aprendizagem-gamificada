package com.grupoenzo.aprendizagem_gamificada.core.exceptions;

public class InsufficientGradeException extends RuntimeException {
    public InsufficientGradeException() {
        super("Insufficient grade to complete course");
    }
}