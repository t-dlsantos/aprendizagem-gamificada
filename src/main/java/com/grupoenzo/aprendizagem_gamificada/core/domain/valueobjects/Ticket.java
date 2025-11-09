package com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects;

import com.grupoenzo.aprendizagem_gamificada.core.domain.abstracts.ValueObject;

public class Ticket extends ValueObject {
    private final int value;

    public Ticket(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isValid() {
        return this.value > 0;
    }
}