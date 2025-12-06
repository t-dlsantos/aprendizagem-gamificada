package com.grupoenzo.recommender.infra.repositories.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recommender_course") // Use a different entity name to avoid conflict in context if any, but table name matches
@Table(name = "course")
public class CourseEntity {
    @Id
    private UUID id;
    private String name;
    private String description;
}
