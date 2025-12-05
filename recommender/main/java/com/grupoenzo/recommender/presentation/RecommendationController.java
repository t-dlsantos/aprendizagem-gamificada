package com.grupoenzo.recommender.presentation.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final GenerateRank generateRank;
    private final RankExplain rankExplain;

    public RecommendationController(GenerateRank generateRank, RankExplain rankExplain) {
        this.generateRank = generateRank;
        this.rankExplain = rankExplain;
    }

    @PostMapping
    public ResponseEntity<RecommendationResponse> recommend(@RequestBody RecommendationRequest request) {
        // 1. Gerar candidatos rankeados
        var candidates = generateRank.execute(
            request.getStudentId(),
            request.getCompletedCourseId(),
            5
        );

        // 2. Explicar com RAG
        var explained = rankExplain.execute(
            new StudentProfile(request.getStudentId()),
            candidates
        );

        // 3. Retornar melhor recomendação
        var best = explained.get(0);
        return ResponseEntity.ok(new RecommendationResponse(
            best.getCourse().getId(),
            best.getCourse().getName(),
            best.getExplanation(),
            best.getRelevanceScore()
        ));
    }
}