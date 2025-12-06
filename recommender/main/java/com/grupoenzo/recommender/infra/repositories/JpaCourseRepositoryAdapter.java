package com.grupoenzo.recommender.infra.repositories;

import com.grupoenzo.recommender.core.domain.Course;
import com.grupoenzo.recommender.core.ports.repositories.CourseRepository;
import com.grupoenzo.recommender.infra.repositories.jpa.SpringDataCourseRepository;
import com.grupoenzo.recommender.infra.repositories.jpa.entity.CourseEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaCourseRepositoryAdapter implements CourseRepository {

    private final SpringDataCourseRepository springDataCourseRepository;

    public JpaCourseRepositoryAdapter(SpringDataCourseRepository springDataCourseRepository) {
        this.springDataCourseRepository = springDataCourseRepository;
    }

    @Override
    public Optional<Course> findById(UUID id) {
        return springDataCourseRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Course> findAll() {
        return springDataCourseRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findByDifficulty(String difficulty) {
        return List.of();
    }

    @Override
    public List<Course> findByTopic(String topic) {
        return List.of();
    }

    private Course toDomain(CourseEntity entity) {
        return new Course(entity.getId(), entity.getName(), entity.getDescription());
    }
}
