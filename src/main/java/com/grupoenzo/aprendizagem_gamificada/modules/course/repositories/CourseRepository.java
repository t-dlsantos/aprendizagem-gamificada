package com.grupoenzo.aprendizagem_gamificada.modules.course.repositories;

import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
}