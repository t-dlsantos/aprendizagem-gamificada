package com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects;

import com.grupoenzo.aprendizagem_gamificada.core.domain.abstracts.ValueObject;
import jakarta.persistence.Embeddable;

@Embeddable
public class Ticket extends ValueObject {
    private int value;

    // JPA requires a no-arg constructor for embeddables
    protected Ticket() {
    }

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