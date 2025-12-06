package com.grupoenzo.recommender.presentation;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class RecommendationResponseTest {

    @Test
    void testConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        String name = "Advanced Java";
        String explanation = "Good fit";
        double score = 0.95;

        RecommendationResponse response = new RecommendationResponse(id, name, explanation, score);

        assertEquals(id, response.getRecommendedCourseId());
        assertEquals(name, response.getCourseName());
        assertEquals(explanation, response.getExplanation());
        assertEquals(score, response.getRelevanceScore());
    }
}
