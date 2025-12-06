package com.grupoenzo.recommender.infra.repositories.jpa;

import com.grupoenzo.recommender.infra.repositories.jpa.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataCourseRepository extends JpaRepository<CourseEntity, UUID> {
}
