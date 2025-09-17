package com.grupoenzo.aprendizagem_gamificada.modules.enrollment.entities;

import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.student.entities.StudentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "enrollment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private CourseEntity course;

    @CreationTimestamp
    private LocalDateTime enrollmentDate;

}