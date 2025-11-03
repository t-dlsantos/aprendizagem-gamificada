package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private int tickets;

    @OneToMany(mappedBy = "student")
    private List<EnrollmentEntity> enrollments;

    public void addTickets(int quantity) {
        this.tickets = this.tickets + quantity;
    }

}