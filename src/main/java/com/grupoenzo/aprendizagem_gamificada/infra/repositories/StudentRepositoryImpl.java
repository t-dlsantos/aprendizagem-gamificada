package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.student.repositories.StudentRepository;

public class StudentRepositoryImpl implements StudentRepository {
    private final StudentRepository studentRepository;

    public StudentRepositoryImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }
}
