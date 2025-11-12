package com.grupoenzo.aprendizagem_gamificada.infra.mappers;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.infra.entity.EnrollmentJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class EnrollmentMapper {
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    public EnrollmentMapper(StudentMapper studentMapper, CourseMapper courseMapper) {
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
    }

    public Enrollment map(EnrollmentJpaEntity entity) {
        return new Enrollment(
                entity.getId(),
                studentMapper.map(entity.getStudent()),
                courseMapper.map(entity.getCourse())
        );
    }

    public List<Enrollment> map(List<EnrollmentJpaEntity> entities) {
        return entities.stream().map(this::map).toList();
    }
}