package com.grupoenzo.recommender.infra.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.grupoenzo.aprendizagem_gamificada.core.domain.events.CourseFinalizedEvent;
import com.grupoenzo.recommender.core.usecases.GenerateRank;
import com.grupoenzo.recommender.core.usecases.RankExplain;
import com.grupoenzo.recommender.core.domain.StudentProfile;

import java.util.UUID;

@Component
public class CourseRecommendationListener {
    private static final Logger logger = LoggerFactory.getLogger(CourseRecommendationListener.class);

    private final GenerateRank generateRank;
    private final RankExplain rankExplain;

    public CourseRecommendationListener(GenerateRank generateRank, RankExplain rankExplain) {
        this.generateRank = generateRank;
        this.rankExplain = rankExplain;
    }

    @EventListener
    @Async
    public void onCourseFinalizedEvent(CourseFinalizedEvent event) {
        logger.info("Iniciando recomendação de curso para estudante: {}", event.getStudentId());
        
        try {
            UUID studentId = event.getStudentId();
            UUID completedCourseId = event.getCourseId();
            
            // 1. Gerar candidatos rankeados
            var candidates = generateRank.execute(
                studentId,
                completedCourseId,
                5
            );

            // 2. Explicar com RAG
            var explained = rankExplain.execute(
                new StudentProfile(studentId),
                candidates
            );

            // 3. Pegar melhor recomendação
            if (!explained.isEmpty()) {
                var best = explained.get(0);
                logger.info("Recomendação gerada com sucesso: {} para estudante: {}", 
                    best.getCourse().getName(), studentId);
                
                // TODO: Salvar recomendação no banco de dados ou enviar para fila de notificação
                logger.debug("Explicação da recomendação: {}", best.getExplanation());
            } else {
                logger.warn("Nenhuma recomendação disponível para estudante: {}", studentId);
            }
        } catch (Exception e) {
            logger.error("Erro ao processar recomendação para estudante: {}", event.getStudentId(), e);
        }
    }
}
