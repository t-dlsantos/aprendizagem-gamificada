package com.grupoenzo.aprendizagem_gamificada.domain.events;

import java.util.UUID;

public record CourseFinalizedEvent(
    UUID enrollmentId,
    UUID studentId,
    UUID courseId,
    Double finalGrade
) {}
