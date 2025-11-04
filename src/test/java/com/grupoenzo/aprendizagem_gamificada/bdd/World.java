package com.grupoenzo.aprendizagem_gamificada.bdd;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.core.useCases.enrollment.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.useCases.enrollment.FinalizeCourseUseCase;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.StudentEntity;
import com.grupoenzo.aprendizagem_gamificada.core.useCases.student.StudentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGradeEntity;
import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;


@Component
@ScenarioScope

public class World {
    public StudentEntity student;
    public CourseEntity course;
    public ModuleEntity module;
    public EnrollmentEntity enrollment;
    public ModuleGradeEntity moduleGrade;
    public EnrollmentRepository EnrollmentRepository;
    public StudentRepository StudentRepository;
    public FinalizeCourseUseCase finalizeCourseUseCase;
    public int ticketsBefore;
}