package com.grupoenzo.recommender.core.usecases;

import com.grupoenzo.recommender.core.domain.Course;
import com.grupoenzo.recommender.core.domain.ExplainedRecommendation;
import com.grupoenzo.recommender.core.domain.RecommendedCourse;
import com.grupoenzo.recommender.core.domain.StudentProfile;
import com.grupoenzo.recommender.core.embeddings.EmbeddingProvider;
import com.grupoenzo.recommender.core.ports.llm.LlmClient;
import com.grupoenzo.recommender.infra.vector.VectorStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class RankExplainTest {

    @Mock
    private LlmClient llm;

    @Mock
    private EmbeddingProvider embeddingProvider;

    @Mock
    private VectorStore vectorStore;

    private RankExplain rankExplain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rankExplain = new RankExplain(llm, embeddingProvider, vectorStore);
    }

    @Test
    void execute_ShouldReturnExplainedRecommendations() {
        UUID studentId = UUID.randomUUID();
        StudentProfile profile = new StudentProfile(studentId);
        
        Course course = new Course(UUID.randomUUID(), "Java Advanced", "Deep dive into Java");
        RecommendedCourse recommendedCourse = new RecommendedCourse(course, 0.9);
        List<RecommendedCourse> candidates = List.of(recommendedCourse);

        when(vectorStore.search(anyString(), anyInt())).thenReturn(List.of("Context 1", "Context 2"));
        when(llm.generate(anyString())).thenReturn("Because you like Java");

        List<ExplainedRecommendation> result = rankExplain.execute(profile, candidates);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Because you like Java", result.get(0).getExplanation());
        assertEquals(course, result.get(0).getCourse());
        assertEquals(0.9, result.get(0).getRelevanceScore());
    }
}
