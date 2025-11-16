package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Module;

import com.grupoenzo.aprendizagem_gamificada.infra.entity.ModuleJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleMapper {
    public Module map(ModuleJpaEntity entity) {
        var jpaCourse = entity.getCourse();
        var course = new Course(jpaCourse.getId(), jpaCourse.getName());

        return new Module(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            course
        );
    }

    public List<Module> map(List<ModuleJpaEntity> entities) {
        return entities.stream().map(this::map).toList();
    }
}