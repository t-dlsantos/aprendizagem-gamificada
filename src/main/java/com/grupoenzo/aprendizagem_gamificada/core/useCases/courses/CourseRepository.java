package com.grupoenzo.aprendizagem_gamificada.core.useCases.courses;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
}