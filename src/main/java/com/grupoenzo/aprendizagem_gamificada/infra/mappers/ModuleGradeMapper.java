package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.ModuleGrade;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.ModuleGradeJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleGradeMapper {
    private final StudentMapper studentMapper;
    private final ModuleMapper moduleMapper;
    private final EnrollmentMapper enrollmentMapper;

    public ModuleGradeMapper(StudentMapper studentMapper, ModuleMapper moduleMapper, EnrollmentMapper enrollmentMapper) {
        this.studentMapper = studentMapper;
        this.moduleMapper = moduleMapper;
        this.enrollmentMapper = enrollmentMapper;
    }

    public ModuleGrade map(ModuleGradeJpaEntity entity) {
        return new ModuleGrade(
                entity.getId(),
                moduleMapper.map(entity.getModule()),
                studentMapper.map(entity.getStudent()),
                enrollmentMapper.map(entity.getEnrollment()),
                entity.getGrade()
        );
    }

    public List<ModuleGrade> map(List<ModuleGradeJpaEntity> entities) {
        return entities.stream().map(this::map).toList();
    }
}