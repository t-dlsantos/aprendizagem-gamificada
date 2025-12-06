package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Module;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.EnrollmentJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.ModuleGradeJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.ModuleJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.StudentJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ModuleGradeMapperTest {

    @Mock
    private StudentMapper studentMapper;
    @Mock
    private ModuleMapper moduleMapper;

    @InjectMocks
    private ModuleGradeMapper mapper;

    @Test
    void map_singleEntity_mapsAllFields() {
        var id = UUID.randomUUID();

        var entity = mock(ModuleGradeJpaEntity.class);
        var moduleJpa = mock(ModuleJpaEntity.class);
        var studentJpa = mock(StudentJpaEntity.class);

        when(entity.getId()).thenReturn(id);
        when(entity.getGrade()).thenReturn(7.5);
        when(entity.getModule()).thenReturn(moduleJpa);
        when(entity.getStudent()).thenReturn(studentJpa);

        var module = new Module(UUID.randomUUID(), "M1", "desc", null);
        var student = new Student(UUID.randomUUID(), "S1", new Ticket(0));

        when(moduleMapper.map(moduleJpa)).thenReturn(module);
        when(studentMapper.map(studentJpa)).thenReturn(student);

        ModuleGrade result = mapper.map(entity);

        assertNotNull(result);
        assertEquals(7.5, result.getGrade());
        assertSame(module, result.getModule());
        assertSame(student, result.getStudent());
        // Enrollment é null pois é definido após o mapeamento pelo EnrollmentMapper
        assertNull(result.getEnrollment());
    }

    @Test
    void map_list_mapsAllEntities() {
        var id1 = UUID.randomUUID();
        var id2 = UUID.randomUUID();

        var entity1 = mock(ModuleGradeJpaEntity.class);
        var entity2 = mock(ModuleGradeJpaEntity.class);

        var moduleJpa = mock(ModuleJpaEntity.class);
        var studentJpa = mock(StudentJpaEntity.class);

        when(entity1.getId()).thenReturn(id1);
        when(entity1.getGrade()).thenReturn(6.0);
        when(entity1.getModule()).thenReturn(moduleJpa);
        when(entity1.getStudent()).thenReturn(studentJpa);

        when(entity2.getId()).thenReturn(id2);
        when(entity2.getGrade()).thenReturn(8.0);
        when(entity2.getModule()).thenReturn(moduleJpa);
        when(entity2.getStudent()).thenReturn(studentJpa);

        var module = new Module(UUID.randomUUID(), "M1", null, null);
        var student = new Student(UUID.randomUUID(), "S1", new Ticket(0));

        when(moduleMapper.map(moduleJpa)).thenReturn(module);
        when(studentMapper.map(studentJpa)).thenReturn(student);

        List<ModuleGrade> list = mapper.map(List.of(entity1, entity2));

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(6.0, list.get(0).getGrade());
        assertEquals(8.0, list.get(1).getGrade());
    }
}
