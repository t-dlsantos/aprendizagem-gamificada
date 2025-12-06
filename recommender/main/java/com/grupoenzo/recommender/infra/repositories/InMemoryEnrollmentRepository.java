package com.grupoenzo.recommender.infra.repositories;

import org.springframework.stereotype.Repository;
import com.grupoenzo.recommender.core.ports.repositories.EnrollmentRepository;
import com.grupoenzo.recommender.core.domain.Enrollment;
import java.util.*;

@Repository
@org.springframework.context.annotation.Profile("mock")
public class InMemoryEnrollmentRepository implements EnrollmentRepository {
    private final List<Enrollment> enrollments = new ArrayList<>();

    @Override
    public List<Enrollment> findByStudentId(UUID studentId) {
        return new ArrayList<>(enrollments);
    }
}
