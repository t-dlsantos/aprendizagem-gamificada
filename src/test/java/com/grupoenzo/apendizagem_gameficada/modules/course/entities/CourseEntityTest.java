package com.grupoenzo.apendizagem_gameficada.modules.course.entities;

import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.ModuleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseEntityTest {

    private CourseEntity course;

    @BeforeEach
    public void setup() {
        this.course = CourseEntity.builder().id(UUID.randomUUID()).name("DevOps").description("A course to master DevOps").build();
    }

    @Test
    public void shouldReturnAvarageGradeCorrectly() {
        course.setModules(Arrays.asList(
                ModuleEntity.builder().description("Module 1").grade(7).build(),
                ModuleEntity.builder().description("Module 2").grade(5).build(),
                ModuleEntity.builder().description("Module 3").grade(9).build()
        ));

        double avarege = course.calculateAverageGrade();

        assertEquals(7, avarege);
    }

    @Test
    public void shouldThrowExceptionWhenNoModules() {
        course.setModules(Collections.emptyList());

        assertThrows(IllegalStateException.class, course::calculateAverageGrade);
    }
}
