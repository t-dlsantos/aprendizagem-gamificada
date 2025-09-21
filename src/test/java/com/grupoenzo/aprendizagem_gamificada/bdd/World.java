package com.grupoenzo.aprendizagem_gamificada.bdd;

import com.grupoenzo.aprendizagem_gamificada.modules.student.entities.StudentEntity;
import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;


@Component
@ScenarioScope
public class World {
    public StudentEntity student;
    public Exception error;
}
