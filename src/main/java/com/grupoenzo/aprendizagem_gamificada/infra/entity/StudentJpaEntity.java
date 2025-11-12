package com.grupoenzo.aprendizagem_gamificada.infra.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "student")
public class StudentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String name;

    @Embedded
    private Ticket ticket;

    @OneToMany(mappedBy = "student")
    private List<EnrollmentJpaEntity> enrollments;

    // TODO: maybe this and ID should be in a abstract Entity class but i'm melted now
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}