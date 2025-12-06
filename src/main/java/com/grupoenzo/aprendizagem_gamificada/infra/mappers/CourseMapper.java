package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.CourseJpaEntity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    private final ModuleMapper moduleMapper;

    public CourseMapper(ModuleMapper moduleMapper) {
        this.moduleMapper = moduleMapper;
    }

    public Course map(CourseJpaEntity entity) {
        return new Course(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                new java.util.ArrayList<>(),
                moduleMapper.map(entity.getModules())
        );
    }
}