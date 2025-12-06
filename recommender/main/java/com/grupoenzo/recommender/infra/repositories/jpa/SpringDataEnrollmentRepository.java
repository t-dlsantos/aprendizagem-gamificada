package com.grupoenzo.recommender.infra.repositories.jpa;

import com.grupoenzo.recommender.infra.repositories.jpa.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataEnrollmentRepository extends JpaRepository<EnrollmentEntity, UUID> {
    List<EnrollmentEntity> findByStudentId(UUID studentId);
}
