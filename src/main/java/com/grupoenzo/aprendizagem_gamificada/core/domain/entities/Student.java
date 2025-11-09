package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.grupoenzo.aprendizagem_gamificada.core.domain.abstracts.Entity;

public class Student extends Entity {
    private String name;
    private int tickets;
    private List<Enrollment> enrollments;

    public Student(UUID id, String name, int tickets) {
        this.id = id;
        this.name = name;
        this.tickets = tickets;
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

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void addTickets(int quantity) {
        this.tickets = this.tickets + quantity;
    }

}