package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.course.repositories.CourseRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.mappers.CourseMapper;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaCourseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CourseRepositoryImpl implements CourseRepository {
    private final JpaCourseRepository jpaRepository;
    private final CourseMapper mapper;

    public CourseRepositoryImpl(JpaCourseRepository jpaCourseRepositoryImpl, CourseMapper mapper) {
        this.jpaRepository = jpaCourseRepositoryImpl;
        this.mapper = mapper;
    }

    @Override
    public Optional<Course> findById(UUID courseId) {
        return jpaRepository.findById(courseId).map(this.mapper::map);
    }
}