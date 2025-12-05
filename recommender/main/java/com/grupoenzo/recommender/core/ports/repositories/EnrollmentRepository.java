package com.grupoenzo.recommender.core.ports.repositories;

import java.util.List;
import java.util.UUID;
import com.grupoenzo.recommender.core.domain.Enrollment;

public interface EnrollmentRepository {
    List<Enrollment> findByStudentId(UUID studentId);
}
