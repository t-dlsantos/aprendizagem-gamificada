package com.grupoenzo.aprendizagem_gamificada.presentation.dtos.requests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FinalizeCourseRequestTest {

    @Test
    void testConstructor() {
        FinalizeCourseRequest request = new FinalizeCourseRequest();
        assertNotNull(request);
    }
}
