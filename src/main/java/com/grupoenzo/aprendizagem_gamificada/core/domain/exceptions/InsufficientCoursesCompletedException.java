package com.grupoenzo.aprendizagem_gamificada.core.domain.exceptions;

public class InsufficientCoursesCompletedException extends RuntimeException {
    public InsufficientCoursesCompletedException() {
        super("Insufficient courses completed");
    }
}