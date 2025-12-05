package com.grupoenzo.recommender.core.ports.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository {
    List<Course> findAll();
    Optional<Course> findById(UUID id);
    List<Course> findByDifficulty(String difficulty);
    List<Course> findByTopic(String topic);
}
