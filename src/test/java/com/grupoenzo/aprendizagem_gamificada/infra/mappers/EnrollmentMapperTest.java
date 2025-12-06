package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.CourseJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.EnrollmentJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.ModuleGradeJpaEntity;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.StudentJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnrollmentMapperTest {

    @Mock
    private StudentMapper studentMapper;
    @Mock
    private CourseMapper courseMapper;
    @Mock
    private ModuleGradeMapper moduleGradeMapper;

    @InjectMocks
    private EnrollmentMapper mapper;

    @Test
    void map_entity_mapsAllFields() {
        var id = UUID.randomUUID();

        var entity = mock(EnrollmentJpaEntity.class);
        var studentJpa = mock(StudentJpaEntity.class);
        var courseJpa = mock(CourseJpaEntity.class);

        when(entity.getId()).thenReturn(id);
        when(entity.getStudent()).thenReturn(studentJpa);
        when(entity.getCourse()).thenReturn(courseJpa);

        var student = new Student(UUID.randomUUID(), "S1", new Ticket(0));
        var course = new Course(UUID.randomUUID(), "C1");

        when(studentMapper.map(studentJpa)).thenReturn(student);
        when(courseMapper.map(courseJpa)).thenReturn(course);

        Enrollment result = mapper.map(entity);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertSame(student, result.getStudent());
        assertSame(course, result.getCourse());
    }

    @Test
    void map_emptyList_returnsEmptyList() {
        var list = mapper.map(List.of());
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void map_list_mapsEntities() {
        var id1 = UUID.randomUUID();
        var id2 = UUID.randomUUID();

        var e1 = mock(EnrollmentJpaEntity.class);
        var e2 = mock(EnrollmentJpaEntity.class);

        var s1 = mock(StudentJpaEntity.class);
        var s2 = mock(StudentJpaEntity.class);
        var c1 = mock(CourseJpaEntity.class);
        var c2 = mock(CourseJpaEntity.class);

        when(e1.getId()).thenReturn(id1);
        when(e1.getStudent()).thenReturn(s1);
        when(e1.getCourse()).thenReturn(c1);

        when(e2.getId()).thenReturn(id2);
        when(e2.getStudent()).thenReturn(s2);
        when(e2.getCourse()).thenReturn(c2);

        var student1 = new Student(UUID.randomUUID(), "S1", new Ticket(0));
        var student2 = new Student(UUID.randomUUID(), "S2", new Ticket(0));
        var course1 = new Course(UUID.randomUUID(), "C1");
        var course2 = new Course(UUID.randomUUID(), "C2");

        when(studentMapper.map(s1)).thenReturn(student1);
        when(studentMapper.map(s2)).thenReturn(student2);
        when(courseMapper.map(c1)).thenReturn(course1);
        when(courseMapper.map(c2)).thenReturn(course2);

        var list = mapper.map(List.of(e1, e2));

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(id1, list.get(0).getId());
        assertSame(student1, list.get(0).getStudent());
        assertSame(course1, list.get(0).getCourse());
        assertEquals(id2, list.get(1).getId());
        assertSame(student2, list.get(1).getStudent());
        assertSame(course2, list.get(1).getCourse());
    }

    @Test
    void map_entityWithModuleGrades_mapsModuleGrades() {
        var id = UUID.randomUUID();
        var mgId1 = UUID.randomUUID();
        var mgId2 = UUID.randomUUID();

        var entity = mock(EnrollmentJpaEntity.class);
        var studentJpa = mock(StudentJpaEntity.class);
        var courseJpa = mock(CourseJpaEntity.class);
        var moduleGradeJpa1 = mock(ModuleGradeJpaEntity.class);
        var moduleGradeJpa2 = mock(ModuleGradeJpaEntity.class);

        when(entity.getId()).thenReturn(id);
        when(entity.getStudent()).thenReturn(studentJpa);
        when(entity.getCourse()).thenReturn(courseJpa);
        when(entity.getModuleGrades()).thenReturn(List.of(moduleGradeJpa1, moduleGradeJpa2));

        var student = new Student(UUID.randomUUID(), "S1", new Ticket(0));
        var course = new Course(UUID.randomUUID(), "C1");
        var moduleGrade1 = new ModuleGrade(mgId1, null, null, null, 8.5);
        var moduleGrade2 = new ModuleGrade(mgId2, null, null, null, 9.0);

        when(studentMapper.map(studentJpa)).thenReturn(student);
        when(courseMapper.map(courseJpa)).thenReturn(course);
        when(moduleGradeMapper.map(moduleGradeJpa1)).thenReturn(moduleGrade1);
        when(moduleGradeMapper.map(moduleGradeJpa2)).thenReturn(moduleGrade2);

        Enrollment result = mapper.map(entity);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertSame(student, result.getStudent());
        assertSame(course, result.getCourse());
        assertNotNull(result.getModuleGrades());
        assertEquals(2, result.getModuleGrades().size());
        assertEquals(moduleGrade1, result.getModuleGrades().get(0));
        assertEquals(moduleGrade2, result.getModuleGrades().get(1));
    }

    @Test
    void map_entityWithNullModuleGrades_handlesGracefully() {
        var id = UUID.randomUUID();

        var entity = mock(EnrollmentJpaEntity.class);
        var studentJpa = mock(StudentJpaEntity.class);
        var courseJpa = mock(CourseJpaEntity.class);

        when(entity.getId()).thenReturn(id);
        when(entity.getStudent()).thenReturn(studentJpa);
        when(entity.getCourse()).thenReturn(courseJpa);
        when(entity.getModuleGrades()).thenReturn(null);

        var student = new Student(UUID.randomUUID(), "S1", new Ticket(0));
        var course = new Course(UUID.randomUUID(), "C1");

        when(studentMapper.map(studentJpa)).thenReturn(student);
        when(courseMapper.map(courseJpa)).thenReturn(course);

        Enrollment result = mapper.map(entity);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void map_entityWithEmptyModuleGrades_handlesGracefully() {
        var id = UUID.randomUUID();

        var entity = mock(EnrollmentJpaEntity.class);
        var studentJpa = mock(StudentJpaEntity.class);
        var courseJpa = mock(CourseJpaEntity.class);

        when(entity.getId()).thenReturn(id);
        when(entity.getStudent()).thenReturn(studentJpa);
        when(entity.getCourse()).thenReturn(courseJpa);
        when(entity.getModuleGrades()).thenReturn(new ArrayList<>());

        var student = new Student(UUID.randomUUID(), "S1", new Ticket(0));
        var course = new Course(UUID.randomUUID(), "C1");

        when(studentMapper.map(studentJpa)).thenReturn(student);
        when(courseMapper.map(courseJpa)).thenReturn(course);

        Enrollment result = mapper.map(entity);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }
}

