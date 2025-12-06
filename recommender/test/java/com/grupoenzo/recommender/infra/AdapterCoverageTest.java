package com.grupoenzo.recommender.infra;

import com.grupoenzo.recommender.core.domain.Course;
import com.grupoenzo.recommender.core.domain.Enrollment;
import com.grupoenzo.recommender.infra.embeddings.OpenAiEmbeddingAdapter;
import com.grupoenzo.recommender.infra.llm.OpenAiLlmClient;
import com.grupoenzo.recommender.infra.repositories.JpaCourseRepositoryAdapter;
import com.grupoenzo.recommender.infra.repositories.JpaEnrollmentRepositoryAdapter;
import com.grupoenzo.recommender.infra.repositories.jpa.SpringDataCourseRepository;
import com.grupoenzo.recommender.infra.repositories.jpa.SpringDataEnrollmentRepository;
import com.grupoenzo.recommender.infra.repositories.jpa.entity.CourseEntity;
import com.grupoenzo.recommender.infra.repositories.jpa.entity.EnrollmentEntity;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdapterCoverageTest {

    @Test
    void testOpenAiEmbeddingAdapter() throws Exception {
        // Instantiate with dummy key
        // Note: This might fail if the constructor validates the key or makes a call.
        // OpenAiEmbeddingModel builder might not validate immediately.
        OpenAiEmbeddingAdapter adapter = new OpenAiEmbeddingAdapter("dummy-key");

        // Mock internal model
        EmbeddingModel mockModel = mock(EmbeddingModel.class);
        Embedding embedding = new Embedding(new float[]{0.1f, 0.2f});
        when(mockModel.embed(anyString())).thenReturn(Response.from(embedding));

        // Set mock via reflection
        Field field = OpenAiEmbeddingAdapter.class.getDeclaredField("embeddingModel");
        field.setAccessible(true);
        field.set(adapter, mockModel);

        float[] result = adapter.embed("text");
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals(0.1f, result[0]);
    }

    @Test
    void testOpenAiLlmClient() throws Exception {
        OpenAiLlmClient client = new OpenAiLlmClient("dummy-key");

        ChatLanguageModel mockModel = mock(ChatLanguageModel.class);
        when(mockModel.generate(anyString())).thenReturn("Response");

        Field field = OpenAiLlmClient.class.getDeclaredField("chatModel");
        field.setAccessible(true);
        field.set(client, mockModel);

        String result = client.generate("prompt");
        assertEquals("Response", result);
    }

    @Test
    void testJpaCourseRepositoryAdapter() {
        SpringDataCourseRepository mockRepo = mock(SpringDataCourseRepository.class);
        JpaCourseRepositoryAdapter adapter = new JpaCourseRepositoryAdapter(mockRepo);

        UUID id = UUID.randomUUID();
        CourseEntity entity = new CourseEntity(id, "Name", "Desc");
        when(mockRepo.findById(id)).thenReturn(Optional.of(entity));

        Optional<Course> result = adapter.findById(id);
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Name", result.get().getTitle());
        assertEquals("Desc", result.get().getDescription());

        // Test findAll
        when(mockRepo.findAll()).thenReturn(List.of(entity));
        List<Course> all = adapter.findAll();
        assertEquals(1, all.size());

        // Test findByDifficulty
        List<Course> byDiff = adapter.findByDifficulty("Easy");
        assertTrue(byDiff.isEmpty());

        // Test findByTopic
        List<Course> byTopic = adapter.findByTopic("Java");
        assertTrue(byTopic.isEmpty());
    }

    @Test
    void testJpaEnrollmentRepositoryAdapter() {
        SpringDataEnrollmentRepository mockRepo = mock(SpringDataEnrollmentRepository.class);
        JpaEnrollmentRepositoryAdapter adapter = new JpaEnrollmentRepositoryAdapter(mockRepo);

        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        CourseEntity courseEntity = new CourseEntity(courseId, "Name", "Desc");
        EnrollmentEntity entity = new EnrollmentEntity(UUID.randomUUID(), studentId, courseEntity);
        
        when(mockRepo.findByStudentId(studentId)).thenReturn(List.of(entity));

        List<Enrollment> result = adapter.findByStudentId(studentId);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(courseId, result.get(0).getCourse().getId());
    }
}
