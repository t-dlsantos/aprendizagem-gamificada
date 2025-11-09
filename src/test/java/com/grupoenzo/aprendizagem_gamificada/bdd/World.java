package com.grupoenzo.aprendizagem_gamificada.bdd;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.CourseEntity;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Module;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.FinalizeCourseUseCase;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.repositories.EnrollmentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.student.repositories.StudentRepository;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;


@Component
@ScenarioScope

public class World {
    public Student student;
    public CourseEntity course;
    public Module module;
    public Enrollment enrollment;
    public ModuleGrade moduleGrade;
    public EnrollmentRepository EnrollmentRepository;
    public StudentRepository StudentRepository;
    public FinalizeCourseUseCase finalizeCourseUseCase;
    public int ticketsBefore;
}