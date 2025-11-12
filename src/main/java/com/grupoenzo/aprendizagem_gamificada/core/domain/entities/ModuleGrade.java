package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.grupoenzo.aprendizagem_gamificada.core.domain.abstracts.Entity;

public class ModuleGrade extends Entity {
    private Module module;
    private Student student;
    private Enrollment enrollment;
    private double grade;
    private LocalDateTime completedAt;

    public ModuleGrade(UUID id, Module module, Student student, Enrollment enrollment, double grade) {
        this.id = id;
        this.module = module;
        this.student = student;
        this.enrollment = enrollment;
        this.grade = grade;
        this.createdAt = LocalDateTime.now();
        this.completedAt = LocalDateTime.now();
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

}