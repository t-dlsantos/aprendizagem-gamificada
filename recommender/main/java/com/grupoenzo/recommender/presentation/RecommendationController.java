package com.grupoenzo.recommender.presentation.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.grupoenzo.recommender.core.usecases.GenerateRank;
import com.grupoenzo.recommender.core.usecases.RankExplain;
import com.grupoenzo.recommender.core.domain.StudentProfile;
import com.grupoenzo.recommender.presentation.RecommendationRequest;
import com.grupoenzo.recommender.presentation.RecommendationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/recommendations")
@Tag(name = "Recommendations", description = "API para recomendações de cursos usando RAG e embeddings")
public class RecommendationController {
    private final GenerateRank generateRank;
    private final RankExplain rankExplain;

    public RecommendationController(GenerateRank generateRank, RankExplain rankExplain) {
        this.generateRank = generateRank;
        this.rankExplain = rankExplain;
    }

    @PostMapping
    @Operation(summary = "Obter recomendação de curso", 
               description = "Gera uma recomendação de curso baseada no curso completado pelo estudante, usando RAG e embeddings para personalização")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recomendação gerada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
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