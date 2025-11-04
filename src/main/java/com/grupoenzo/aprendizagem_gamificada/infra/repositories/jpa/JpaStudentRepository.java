package com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaStudentRepository extends JpaRepository<StudentEntity, UUID> {
}
