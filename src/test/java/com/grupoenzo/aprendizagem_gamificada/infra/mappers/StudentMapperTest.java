package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.StudentJpaEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class StudentMapperTest {

    @Test
    void mapFromJpaToDomainAndBack() {
        var id = UUID.randomUUID();
        var jpa = StudentJpaEntity.builder()
                .id(id)
                .name("Thiago")
                .ticket(new Ticket(2))
                .build();

        var mapper = new StudentMapper();

        Student domain = mapper.map(jpa);
        assertEquals(id, domain.getId());
        assertEquals("Thiago", domain.getName());
        assertEquals(2, domain.getTicket().getValue());

        var back = mapper.map(domain);
        assertEquals(id, back.getId());
        assertEquals("Thiago", back.getName());
        assertNotNull(back.getTicket());
        assertEquals(2, back.getTicket().getValue());
    }
}
