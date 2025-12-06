package com.grupoenzo.aprendizagem_gamificada.infra.http;

import com.grupoenzo.aprendizagem_gamificada.presentation.dtos.requests.RecommendationRequest;
import com.grupoenzo.aprendizagem_gamificada.presentation.dtos.responses.RecommendationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class RecommenderClientTest {

    @Mock
    private RestTemplate restTemplate;

    private RecommenderClient recommenderClient;
    private final String recommenderUrl = "http://localhost:8081";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recommenderClient = new RecommenderClient(restTemplate, recommenderUrl);
    }

    @Test
    void getRecommendation_ShouldReturnResponse_WhenSuccessful() {
        RecommendationRequest request = new RecommendationRequest();
        RecommendationResponse expectedResponse = new RecommendationResponse();
        expectedResponse.setCourseName("Test Course");

        when(restTemplate.postForObject(eq(recommenderUrl + "/api/recommendations"), any(RecommendationRequest.class), eq(RecommendationResponse.class)))
                .thenReturn(expectedResponse);

        RecommendationResponse actualResponse = recommenderClient.getRecommendation(request);

        assertNotNull(actualResponse);
        assertEquals("Test Course", actualResponse.getCourseName());
    }

    @Test
    void getRecommendation_ShouldReturnNull_WhenExceptionOccurs() {
        RecommendationRequest request = new RecommendationRequest();

        when(restTemplate.postForObject(eq(recommenderUrl + "/api/recommendations"), any(RecommendationRequest.class), eq(RecommendationResponse.class)))
                .thenThrow(new RuntimeException("Connection refused"));

        RecommendationResponse actualResponse = recommenderClient.getRecommendation(request);

        assertNull(actualResponse);
    }
}
