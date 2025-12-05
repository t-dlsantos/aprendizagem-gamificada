package com.grupoenzo.aprendizagem_gamificada.presentation.controller.enrollment;

import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Enrollment;
import com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Student;
import com.grupoenzo.aprendizagem_gamificada.core.domain.valueobjects.Ticket;
import com.grupoenzo.aprendizagem_gamificada.core.usecases.enrollment.FinalizeCourseUseCase;
import com.grupoenzo.aprendizagem_gamificada.infra.http.RecommenderClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentControllerTest {

    @Test
    void finalizeReturnsOkWhenUseCaseSucceeds() {
        var useCase = Mockito.mock(FinalizeCourseUseCase.class);
        var recommenderClient = Mockito.mock(RecommenderClient.class);
        var student = new Student(UUID.randomUUID(), "T", new Ticket(0));
        var enrollment = new Enrollment(UUID.randomUUID(), student, new com.grupoenzo.aprendizagem_gamificada.core.domain.entities.Course(UUID.randomUUID(), "C"));

        Mockito.when(useCase.execute(enrollment.getId())).thenReturn(enrollment);

        var controller = new EnrollmentController(useCase, recommenderClient);

        ResponseEntity<Object> resp = controller.finalize(enrollment.getId());

        assertEquals(200, resp.getStatusCodeValue());
        assertSame(enrollment, resp.getBody());
    }

    @Test
    void finalizeReturnsBadRequestWhenUseCaseThrows() {
        var useCase = Mockito.mock(FinalizeCourseUseCase.class);
        var recommenderClient = Mockito.mock(RecommenderClient.class);
        var id = UUID.randomUUID();
        Mockito.when(useCase.execute(id)).thenThrow(new RuntimeException("boom"));

        var controller = new EnrollmentController(useCase, recommenderClient);

        ResponseEntity<Object> resp = controller.finalize(id);

        assertEquals(400, resp.getStatusCodeValue());
        assertEquals("boom", resp.getBody());
    }
}
