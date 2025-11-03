package com.grupoenzo.aprendizagem_gamificada.core.useCases.student;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
}