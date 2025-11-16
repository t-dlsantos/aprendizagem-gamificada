package com.grupoenzo.aprendizagem_gamificada.infra.repositories;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.StudentJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.repositories.jpa.JpaStudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class StudentRepositoryIntegrationTest {

    @Autowired
    private JpaStudentRepository jpaStudentRepository;

    @Autowired
    private StudentRepositoryImpl studentRepository;

    @Test
    void save_shouldPersistAndReturnDomainStudent() {
        StudentJpaEntity student = StudentJpaEntity.builder()
                .name("Integration Student")
                .build();
        student = jpaStudentRepository.save(student);

        // map saved JPA entity back to domain using repository impl
        var domain = studentRepository.save(new com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student(
                student.getId(), student.getName(), null));

        assertNotNull(domain);
        assertEquals(student.getId(), domain.getId());
        assertEquals(student.getName(), domain.getName());
    }
}
