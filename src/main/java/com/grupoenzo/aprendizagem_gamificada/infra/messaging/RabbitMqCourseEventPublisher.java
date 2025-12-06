package com.grupoenzo.aprendizagem_gamificada.infra.messaging;

import com.grupoenzo.aprendizagem_gamificada.domain.events.CourseFinalizedEvent;
import com.grupoenzo.aprendizagem_gamificada.infra.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqCourseEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishCourseFinalized(CourseFinalizedEvent event) {
        log.info("Enviando evento de curso finalizado para RabbitMQ: {}", event);
        rabbitTemplate.convertAndSend(RabbitMqConfig.COURSE_FINALIZED_QUEUE, event);
    }
}
