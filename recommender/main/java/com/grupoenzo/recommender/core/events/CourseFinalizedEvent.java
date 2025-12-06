package com.grupoenzo.recommender.core.events;

import java.util.UUID;

public record CourseFinalizedEvent(
    UUID enrollmentId,
    UUID studentId,
    UUID courseId,
    Double finalGrade
) {}
