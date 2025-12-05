package com.grupoenzo.recommender.core.domain;

import java.util.UUID;
import java.util.List;
import java.util.Collections;

public class StudentProfile {
    private final UUID studentId;
    public StudentProfile(UUID studentId) { this.studentId = studentId; }

    public String getName() { return ""; }
    public List<String> getCompletedCourses() { return Collections.emptyList(); }
    public String getLevel() { return "beginner"; }
    public UUID getStudentId() { return studentId; }
}
