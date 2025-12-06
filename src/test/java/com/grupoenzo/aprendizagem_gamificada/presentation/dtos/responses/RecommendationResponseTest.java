package com.grupoenzo.aprendizagem_gamificada.presentation.dtos.responses;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class RecommendationResponseTest {

    @Test
    void testGettersAndSetters() {
        RecommendationResponse response = new RecommendationResponse();
        UUID id = UUID.randomUUID();
        String name = "Advanced Java";
        String explanation = "Because you liked Java";
        double score = 0.9;

        response.setRecommendedCourseId(id);
        response.setCourseName(name);
        response.setExplanation(explanation);
        response.setRelevanceScore(score);

        assertEquals(id, response.getRecommendedCourseId());
        assertEquals(name, response.getCourseName());
        assertEquals(explanation, response.getExplanation());
        assertEquals(score, response.getRelevanceScore());
    }
}
