package com.grupoenzo.aprendizagem_gamificada.modules.student.repositories;

import com.grupoenzo.aprendizagem_gamificada.modules.student.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
}