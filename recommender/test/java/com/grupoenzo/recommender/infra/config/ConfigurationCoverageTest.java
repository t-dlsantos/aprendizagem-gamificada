package com.grupoenzo.recommender.infra.config;

import com.grupoenzo.recommender.core.domain.Course;
import com.grupoenzo.recommender.core.embeddings.EmbeddingProvider;
import com.grupoenzo.recommender.core.ports.llm.LlmClient;
import com.grupoenzo.recommender.core.ports.repositories.CourseRepository;
import com.grupoenzo.recommender.core.ports.repositories.EnrollmentRepository;
import com.grupoenzo.recommender.core.usecases.GenerateRank;
import com.grupoenzo.recommender.core.usecases.RankExplain;
import com.grupoenzo.recommender.infra.vector.VectorStore;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ConfigurationCoverageTest {

    @Test
    void testRabbitMqConfig() {
        RabbitMqConfig config = new RabbitMqConfig();
        
        Queue queue = config.queue();
        assertNotNull(queue);
        assertEquals(RabbitMqConfig.COURSE_FINALIZED_QUEUE, queue.getName());
        
        MessageConverter converter = config.jsonMessageConverter();
        assertNotNull(converter);
        
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        SimpleRabbitListenerContainerFactory factory = config.rabbitListenerContainerFactory(connectionFactory);
        assertNotNull(factory);
    }

    @Test
    void testSwaggerConfig() {
        SwaggerConfig config = new SwaggerConfig();
        OpenAPI openAPI = config.openAPI();
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("Recommender API", openAPI.getInfo().getTitle());
    }

    @Test
    void testRecommenderConfig() {
        RecommenderConfig config = new RecommenderConfig();
        
        EmbeddingProvider embeddingProvider = mock(EmbeddingProvider.class);
        CourseRepository courseRepository = mock(CourseRepository.class);
        EnrollmentRepository enrollmentRepository = mock(EnrollmentRepository.class);
        LlmClient llmClient = mock(LlmClient.class);
        VectorStore vectorStore = mock(VectorStore.class);

        Course course = new Course(UUID.randomUUID(), "Title", "Desc");
        when(courseRepository.findAll()).thenReturn(List.of(course));
        when(embeddingProvider.embed(anyString())).thenReturn(new float[]{0.1f});

        VectorStore vs = config.vectorStore(embeddingProvider, courseRepository);
        assertNotNull(vs);

        GenerateRank gr = config.generateRank(courseRepository, enrollmentRepository, embeddingProvider);
        assertNotNull(gr);
        
        RankExplain re = config.rankExplain(llmClient, embeddingProvider, vectorStore);
        assertNotNull(re);

        // Test empty courses
        when(courseRepository.findAll()).thenReturn(List.of());
        VectorStore vsEmpty = config.vectorStore(embeddingProvider, courseRepository);
        assertNotNull(vsEmpty);

        // Test null courses
        when(courseRepository.findAll()).thenReturn(null);
        VectorStore vsNull = config.vectorStore(embeddingProvider, courseRepository);
        assertNotNull(vsNull);
    }
}
