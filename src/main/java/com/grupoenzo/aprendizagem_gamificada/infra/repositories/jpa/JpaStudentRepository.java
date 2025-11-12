package com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa;

import com.grupoenzo.aprendizagem_gamificada.infra.entity.StudentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaStudentRepository extends JpaRepository<StudentJpaEntity, UUID> {
}
