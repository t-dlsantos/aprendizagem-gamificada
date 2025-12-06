package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.grupoenzo.aprendizagem_gamificada.core.domain.abstracts.Entity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket;

public class Student extends Entity {
    private String name;
    private Ticket ticket;
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student(UUID id, String name, Ticket ticket) {
        this.id = id;
        this.name = name;
        this.ticket = ticket;
        this.enrollments = new ArrayList<>();
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

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void addTickets(int quantity) {
        int currentValue = this.ticket.getValue();
        this.setTicket(new Ticket(currentValue + quantity));
    }

}