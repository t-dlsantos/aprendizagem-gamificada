package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Module;

import com.grupoenzo.aprendizagem_gamificada.infra.entity.ModuleJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleMapper {
    private final CourseMapper courseMapper;

    public ModuleMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public Module map(ModuleJpaEntity entity) {
        return new Module(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                courseMapper.map(entity.getCourse())
        );
    }

    public List<Module> map(List<ModuleJpaEntity> entities) {
        return entities.stream().map(this::map).toList();
    }
}