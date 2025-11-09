package com.grupoenzo.aprendizagem_gamificada.core.usecases.student.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;

public interface StudentRepository {
    Student save(Student student);
}