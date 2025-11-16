package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Module;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.CourseJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.ModuleJpaEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ModuleMapperTest {

    @Test
    void mapModuleEntityToDomain() {
        var courseId = UUID.randomUUID();
        var courseJpa = CourseJpaEntity.builder().id(courseId).name("DevOps").build();

        var moduleId = UUID.randomUUID();
        var moduleJpa = ModuleJpaEntity.builder()
                .id(moduleId)
                .name("Module 1")
                .description("desc")
                .course(courseJpa)
                .build();

        var mapper = new ModuleMapper();
        Module module = mapper.map(moduleJpa);

        assertEquals(moduleId, module.getId());
        assertEquals("Module 1", module.getName());
        assertEquals("desc", module.getDescription());
        assertNotNull(module.getCourse());
        assertEquals(courseId, module.getCourse().getId());
    }
}
