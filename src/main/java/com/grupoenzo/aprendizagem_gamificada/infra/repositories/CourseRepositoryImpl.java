package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.course.repositories.CourseRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaCourseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CourseRepositoryImpl implements CourseRepository {
    private final JpaCourseRepository jpaRepository;

    public CourseRepositoryImpl(JpaCourseRepository jpaCourseRepositoryImpl) {
        this.jpaRepository = jpaCourseRepositoryImpl;
    }

    @Override
    public Optional<Course> findById(UUID courseId) {
        return jpaRepository.findById(courseId);
    }
}