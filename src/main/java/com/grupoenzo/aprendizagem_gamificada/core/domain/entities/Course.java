package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import java.util.List;
import java.util.UUID;

import com.grupoenzo.aprendizagem_gamificada.core.domain.abstracts.Entity;

public class Course extends Entity {
    private String name;
    private String description;
    private List<Enrollment> enrollments;
    private List<Module> modules;

    public Course(UUID id, String name, String description, List<Enrollment> enrollments, List<Module> modules) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.enrollments = enrollments;
        this.modules = modules;
    }

    public Course(UUID id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}