package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.CourseJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    private final EnrollmentMapper enrollmentMapper;
    private final ModuleMapper moduleMapper;

    public CourseMapper(EnrollmentMapper enrollmentMapper,  ModuleMapper moduleMapper) {
        this.enrollmentMapper = enrollmentMapper;
        this.moduleMapper = moduleMapper;
    }

    public Course map(CourseJpaEntity entity) {
        return new Course(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                enrollmentMapper.map(entity.getEnrollments()),
                moduleMapper.map(entity.getModules())
        );
    }
}