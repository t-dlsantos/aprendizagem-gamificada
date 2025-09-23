package com.grupoenzo.aprendizagem_gamificada.exceptions;

public class InsufficientCoursesCompletedException extends RuntimeException {
    public InsufficientCoursesCompletedException() {
        super("Insufficient courses completed");
    }
}