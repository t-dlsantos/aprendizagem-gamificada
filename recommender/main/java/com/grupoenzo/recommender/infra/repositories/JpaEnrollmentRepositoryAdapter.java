package com.grupoenzo.recommender.infra.repositories;

import com.grupoenzo.recommender.core.domain.Course;
import com.grupoenzo.recommender.core.domain.Enrollment;
import com.grupoenzo.recommender.core.ports.repositories.EnrollmentRepository;
import com.grupoenzo.recommender.infra.repositories.jpa.SpringDataEnrollmentRepository;
import com.grupoenzo.recommender.infra.repositories.jpa.entity.EnrollmentEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaEnrollmentRepositoryAdapter implements EnrollmentRepository {

    private final SpringDataEnrollmentRepository springDataEnrollmentRepository;

    public JpaEnrollmentRepositoryAdapter(SpringDataEnrollmentRepository springDataEnrollmentRepository) {
        this.springDataEnrollmentRepository = springDataEnrollmentRepository;
    }

    @Override
    public List<Enrollment> findByStudentId(UUID studentId) {
        return springDataEnrollmentRepository.findByStudentId(studentId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Enrollment toDomain(EnrollmentEntity entity) {
        // Course mapping
        Course course = new Course(entity.getCourse().getId(), entity.getCourse().getName());
        return new Enrollment(course);
    }
}
