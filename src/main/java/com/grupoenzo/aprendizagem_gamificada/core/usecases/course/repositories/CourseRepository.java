package com.grupoenzo.aprendizagem_gamificada.core.usecases.course.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository {
    Optional<Course> findById(UUID courseId);
}