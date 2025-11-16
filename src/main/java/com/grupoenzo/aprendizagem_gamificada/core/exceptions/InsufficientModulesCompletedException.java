package com.grupoenzo.aprendizagem_gamificada.core.exceptions;

public class InsufficientModulesCompletedException extends RuntimeException {
    public InsufficientModulesCompletedException() {
        super("Insufficient modules completed");
    }
}