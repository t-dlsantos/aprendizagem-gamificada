package com.grupoenzo.aprendizagem_gamificada.core.useCases.student;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.StudentEntity;

public interface StudentRepository {
    StudentEntity save(StudentEntity student);
}