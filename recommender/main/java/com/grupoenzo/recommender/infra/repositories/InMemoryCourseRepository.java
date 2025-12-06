package com.grupoenzo.recommender.infra.repositories;

import org.springframework.stereotype.Repository;
import com.grupoenzo.recommender.core.ports.repositories.CourseRepository;
import com.grupoenzo.recommender.core.domain.Course;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@org.springframework.context.annotation.Profile("mock")
public class InMemoryCourseRepository implements CourseRepository {
    private final Map<UUID, Course> courses = new ConcurrentHashMap<>();

    @Override
    public Optional<Course> findById(UUID id) {
        return Optional.ofNullable(courses.get(id));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    @Override
    public List<Course> findByDifficulty(String difficulty) {
        return new ArrayList<>();
    }

    @Override
    public List<Course> findByTopic(String topic) {
        return new ArrayList<>();
    }
}
