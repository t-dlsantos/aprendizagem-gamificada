package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import com.grupoenzo.aprendizagem_gamificada.core.domain.abstracts.Entity;
import com.grupoenzo.aprendizagem_gamificada.exceptions.InsufficientCoursesCompletedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Enrollment extends Entity {
    private Student student;
    private Course course;
    private List<ModuleGrade> moduleGrades;

    public Enrollment(UUID id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.moduleGrades = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<ModuleGrade> getModuleGrades() {
        return moduleGrades;
    }

    public void setModuleGrades(List<ModuleGrade> moduleGrades) {
        this.moduleGrades = moduleGrades;
    }

    public double calculateAverageGrade() {
        if (moduleGrades == null || moduleGrades.isEmpty() || moduleGrades.size() != course.getModules().size()) {
            throw new InsufficientCoursesCompletedException();
        }

        return moduleGrades.stream()
                .mapToDouble(ModuleGrade::getGrade)
                .average()
                .getAsDouble();
    }
}