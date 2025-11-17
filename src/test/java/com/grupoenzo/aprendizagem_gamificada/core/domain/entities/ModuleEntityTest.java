package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ModuleEntityTest {

    @Test
    void setters_work() {
        var course = new Course(UUID.randomUUID(), "DevOps");
        var module = new Module(UUID.randomUUID(), "M1", "d", course);
        assertEquals("M1", module.getName());

        module.setName("M1-upd");
        assertEquals("M1-upd", module.getName());

        module.setDescription("newdesc");
        assertEquals("newdesc", module.getDescription());
    }
}
