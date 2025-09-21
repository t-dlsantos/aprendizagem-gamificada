package com.grupoenzo.aprendizagem_gamificada.bdd.steps;


import com.grupoenzo.aprendizagem_gamificada.bdd.World;
import com.grupoenzo.aprendizagem_gamificada.modules.student.entities.StudentEntity;

final class steps_support {
    private steps_support() {}
    static void basicStudent(World world, String name) {
        world.student = StudentEntity.builder().name(name).build();
    }
}
