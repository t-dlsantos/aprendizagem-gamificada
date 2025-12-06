package com.grupoenzo.recommender.infra.messaging;

import com.grupoenzo.recommender.core.events.CourseFinalizedEvent;
import com.grupoenzo.recommender.core.usecases.GenerateRank;
import com.grupoenzo.recommender.core.usecases.RankExplain;
import com.grupoenzo.recommender.infra.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseFinalizedListener {

    private final GenerateRank generateRank;
    private final RankExplain rankExplain;

    @RabbitListener(queues = RabbitMqConfig.COURSE_FINALIZED_QUEUE)
    public void handleCourseFinalized(CourseFinalizedEvent event) {
        log.info("Evento recebido via RabbitMQ: {}", event);

        try {
            // 1. Gera o Rank baseado no curso finalizado (Limitando a 5 recomendações)
            var rankedCourses = generateRank.execute(event.studentId(), event.courseId(), 5);
            
            if (rankedCourses.isEmpty()) {
                log.warn("Nenhuma recomendação encontrada para o curso {}", event.courseId());
                return;
            }

            // 2. Pega o top 1
            var topRecommendation = rankedCourses.get(0);

            // 3. Gera a explicação com a IA
            // Precisamos adaptar os dados para o formato que o RankExplain espera
            var studentProfile = new com.grupoenzo.recommender.core.domain.StudentProfile(event.studentId());
            var explanations = rankExplain.execute(
                studentProfile, 
                java.util.List.of(topRecommendation)
            );

            log.info("Recomendação gerada com sucesso: {}", explanations);
            
            // Aqui você poderia salvar essa recomendação no banco ou notificar o usuário via WebSocket/Email
            
        } catch (Exception e) {
            log.error("Erro ao processar recomendação para o evento: {}", event, e);
            // Em um cenário real, poderíamos jogar para uma Dead Letter Queue (DLQ)
        }
    }
}
