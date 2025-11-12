package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.student.repositories.StudentRepository;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.StudentJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.mappers.StudentMapper;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaStudentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final JpaStudentRepository jpaRepository;
    private final StudentMapper mapper;

    public StudentRepositoryImpl(JpaStudentRepository jpaStudentRepository, StudentMapper mapper) {
        this.jpaRepository =  jpaStudentRepository;
        this.mapper = mapper;
    }

    @Override
    public Student save(Student student) {
        StudentJpaEntity entity = mapper.map(student);
        StudentJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.map(savedEntity);
    }
}
