package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.grupoenzo.aprendizagem_gamificada.core.domain.abstracts.Entity;

public class Module extends Entity {
    private String name;
    private String description;
    private Course course;

    public Module(UUID id, String name, String description, Course course) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.course = course;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}