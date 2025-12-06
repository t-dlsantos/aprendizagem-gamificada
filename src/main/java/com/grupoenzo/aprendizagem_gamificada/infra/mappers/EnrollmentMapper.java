package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.EnrollmentJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrollmentMapper {
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final ModuleGradeMapper moduleGradeMapper;

    public EnrollmentMapper(StudentMapper studentMapper, CourseMapper courseMapper, ModuleGradeMapper moduleGradeMapper) {
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
        this.moduleGradeMapper = moduleGradeMapper;
    }

    public Enrollment map(EnrollmentJpaEntity entity) {
        Enrollment enrollment = new Enrollment(
                entity.getId(),
                studentMapper.map(entity.getStudent()),
                courseMapper.map(entity.getCourse())
        );
        
        // Mapear moduleGrades se disponíveis
        if (entity.getModuleGrades() != null && !entity.getModuleGrades().isEmpty()) {
            var mappedModuleGrades = entity.getModuleGrades().stream()
                    .map(moduleGradeMapper::map)
                    .toList();
            
            // Definir o enrollment para cada moduleGrade mapeado
            mappedModuleGrades.forEach(mg -> mg.setEnrollment(enrollment));
            
            enrollment.setModuleGrades(mappedModuleGrades);
        }
        
        return enrollment;
    }

    public List<Enrollment> map(List<EnrollmentJpaEntity> entities) {
        return entities.stream().map(this::map).toList();
    }
}