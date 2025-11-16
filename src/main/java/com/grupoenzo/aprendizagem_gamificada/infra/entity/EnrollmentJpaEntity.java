package com.grupoenzo.aprendizagem_gamificada.infra.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.grupoenzo.aprendizagem_gamificada.core.domain.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "enrollment")
public class EnrollmentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentJpaEntity student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseJpaEntity course;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}