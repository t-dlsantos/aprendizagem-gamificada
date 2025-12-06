package com.grupoenzo.aprendizagem_gamificada.infra.messaging;

import com.grupoenzo.aprendizagem_gamificada.domain.events.CourseFinalizedEvent;
import com.grupoenzo.aprendizagem_gamificada.infra.config.RabbitMqConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

import static org.mockito.Mockito.verify;

class RabbitMqCourseEventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    private RabbitMqCourseEventPublisher publisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        publisher = new RabbitMqCourseEventPublisher(rabbitTemplate);
    }

    @Test
    void publishCourseFinalized_ShouldSendMessageToQueue() {
        CourseFinalizedEvent event = new CourseFinalizedEvent(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 9.5);

        publisher.publishCourseFinalized(event);

        verify(rabbitTemplate).convertAndSend(RabbitMqConfig.COURSE_FINALIZED_QUEUE, event);
    }
}
