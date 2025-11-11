package com.grupoenzo.aprendizagem_gamificada.infra.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "module_grade")
public class ModuleGradeJpaEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private ModuleJpaEntity module;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentJpaEntity student;

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private EnrollmentJpaEntity enrollment;

}