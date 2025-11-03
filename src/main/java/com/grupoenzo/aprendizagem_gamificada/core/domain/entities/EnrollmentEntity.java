package com.grupoenzo.aprendizagem_gamificada.core.domain.entities;

import com.grupoenzo.aprendizagem_gamificada.exceptions.InsufficientCoursesCompletedException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
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

    @OneToMany(mappedBy = "enrollment")
    private List<ModuleGradeEntity> moduleGrades;

    @CreationTimestamp
    private LocalDateTime enrollmentDate;

    public double calculateAverageGrade() {
        if (moduleGrades == null || moduleGrades.isEmpty() || moduleGrades.size() != course.getModules().size()) {
            throw new InsufficientCoursesCompletedException();
        }

        return moduleGrades.stream()
            .mapToDouble(ModuleGradeEntity::getGrade)
            .average()
            .getAsDouble();
    }
}