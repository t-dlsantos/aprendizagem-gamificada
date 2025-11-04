package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.StudentEntity;
import com.grupoenzo.aprendizagem_gamificada.core.useCases.student.StudentRepository;

public class StudentRepositoryImpl implements StudentRepository {
    private final StudentRepository studentRepository;

    public StudentRepositoryImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentEntity save(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }
}
