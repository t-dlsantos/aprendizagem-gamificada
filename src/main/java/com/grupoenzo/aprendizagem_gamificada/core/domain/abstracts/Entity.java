package com.grupoenzo.aprendizagem_gamificada.core.domain.abstracts;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Entity {
    protected UUID id;
    protected LocalDateTime createdAt;
}