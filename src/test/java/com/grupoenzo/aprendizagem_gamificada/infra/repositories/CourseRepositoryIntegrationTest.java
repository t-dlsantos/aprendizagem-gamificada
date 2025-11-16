package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.CourseJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaCourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CourseRepositoryIntegrationTest {

    @Autowired
    private JpaCourseRepository jpaCourseRepository;

    @Autowired
    private CourseRepositoryImpl courseRepository;

    @Test
    @Transactional
    void findById_shouldReturnMappedDomainCourse() {
        CourseJpaEntity course = CourseJpaEntity.builder()
                .name("Integration Course")
                .description("desc")
                .build();
        // ensure modules list is non-null
        course.setModules(java.util.List.of());
        course = jpaCourseRepository.save(course);

        Optional<Course> found = courseRepository.findById(course.getId());

        assertTrue(found.isPresent());
        Course domain = found.get();
        assertEquals(course.getId(), domain.getId());
        assertEquals(course.getName(), domain.getName());
    }
}
