package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.CourseJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.EnrollmentJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.StudentJpaEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentMapperTest {

    @Test
    void mapEnrollmentEntityToDomain() {
        var studentJpa = StudentJpaEntity.builder().id(UUID.randomUUID()).name("Thiago").ticket(new com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket(1)).build();
        var courseJpa = CourseJpaEntity.builder().id(UUID.randomUUID()).name("DevOps").build();
        courseJpa.setModules(java.util.List.of());

        var enrollmentJpa = EnrollmentJpaEntity.builder()
                .id(UUID.randomUUID())
                .student(studentJpa)
                .course(courseJpa)
                .build();

        var studentMapper = new StudentMapper();
        var courseMapper = new CourseMapper(new ModuleMapper());
        var mapper = new EnrollmentMapper(studentMapper, courseMapper);

        Enrollment domain = mapper.map(enrollmentJpa);

        assertEquals(enrollmentJpa.getId(), domain.getId());
        assertEquals("Thiago", domain.getStudent().getName());
        assertEquals("DevOps", domain.getCourse().getName());
    }
}
