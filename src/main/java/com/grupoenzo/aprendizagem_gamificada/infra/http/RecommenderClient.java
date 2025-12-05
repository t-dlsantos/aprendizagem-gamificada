package com.grupoenzo.aprendizagem_gamificada.infra.http;

import com.grupoenzo.aprendizagem_gamificada.presentation.dtos.requests.RecommendationRequest;
import com.grupoenzo.aprendizagem_gamificada.presentation.dtos.responses.RecommendationResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Component
public class RecommenderClient {
    private final RestTemplate restTemplate;
    private final String recommenderUrl;

    public RecommenderClient(RestTemplate restTemplate, @Value("${recommender.url}") String recommenderUrl) {
        this.restTemplate = restTemplate;
        this.recommenderUrl = recommenderUrl;
    }

    public RecommendationResponse getRecommendation(RecommendationRequest request) {
        try {
            return restTemplate.postForObject(
                recommenderUrl + "/api/recommendations",
                request,
                RecommendationResponse.class
            );
        } catch (Exception e) {
            return null;
        }
    }
}
