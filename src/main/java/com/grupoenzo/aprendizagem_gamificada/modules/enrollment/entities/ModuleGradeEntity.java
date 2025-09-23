package com.grupoenzo.aprendizagem_gamificada.modules.enrollment.entities;

import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.ModuleEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.student.entities.StudentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "module_grade")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleGradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private ModuleEntity module;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private EnrollmentEntity enrollment;

    private double grade;

    @CreationTimestamp
    private LocalDateTime completedAt;
}