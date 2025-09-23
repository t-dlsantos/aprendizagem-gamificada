package com.grupoenzo.aprendizagem_gamificada.bdd;

import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.ModuleEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.entities.EnrollmentEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.useCases.FinalizeCourseUseCase;
import com.grupoenzo.aprendizagem_gamificada.modules.student.entities.StudentEntity;
import com.grupoenzo.aprendizagem_gamificada.modules.student.repositories.StudentRepository;
import com.grupoenzo.aprendizagem_gamificada.modules.enrollment.entities.ModuleGradeEntity;
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
    public EnrollmentRepository enrollmentRepository;
    public StudentRepository studentRepository;
    public FinalizeCourseUseCase finalizeCourseUseCase;
    public int ticketsBefore;
}