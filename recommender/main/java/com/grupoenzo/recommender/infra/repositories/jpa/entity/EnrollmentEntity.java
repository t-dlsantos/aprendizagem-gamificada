package com.grupoenzo.recommender.infra.repositories.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recommender_enrollment")
@Table(name = "enrollment")
public class EnrollmentEntity {
    @Id
    private UUID id;

    @Column(name = "student_id")
    private UUID studentId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;
}
