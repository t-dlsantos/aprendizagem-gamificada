package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.StudentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public Student map(StudentJpaEntity entity) {
        return new Student(
                entity.getId(),
                entity.getName(),
                entity.getTicket()
        );
    }

    public StudentJpaEntity map(Student entity) {
        return StudentJpaEntity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ticket(entity.getTicket())
                .build();
    }
}