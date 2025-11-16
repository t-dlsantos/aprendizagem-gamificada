package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.CourseJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.ModuleJpaEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CourseMapperTest {

    @Test
    void mapCourseEntityToDomainWithModules() {
        var courseId = UUID.randomUUID();
        var courseJpa = CourseJpaEntity.builder().id(courseId).name("DevOps").build();

        var moduleJpa = ModuleJpaEntity.builder()
                .id(UUID.randomUUID())
                .name("M1")
                .description("d")
                .course(courseJpa)
                .build();

        courseJpa.setModules(List.of(moduleJpa));

        var moduleMapper = new ModuleMapper();
        var courseMapper = new CourseMapper(moduleMapper);

        Course course = courseMapper.map(courseJpa);
        assertEquals(courseId, course.getId());
        assertEquals("DevOps", course.getName());
        assertNotNull(course.getModules());
        assertEquals(1, course.getModules().size());
        assertEquals("M1", course.getModules().get(0).getName());
    }
}
