package com.grupoenzo.aprendizagem_gamificada.core.domain.events;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class CourseFinalizedEvent extends ApplicationEvent {
    private final UUID studentId;
    private final UUID courseId;
    private final double finalGrade;

    public CourseFinalizedEvent(Object source, UUID studentId, UUID courseId, double finalGrade) {
        super(source);
        this.studentId = studentId;
        this.courseId = courseId;
        this.finalGrade = finalGrade;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public double getFinalGrade() {
        return finalGrade;
    }
}
