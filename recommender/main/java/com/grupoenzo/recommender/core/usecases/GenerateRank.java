package com.grupoenzo.recommender.core.usecases;

import com.grupoenzo.recommender.core.ports.repositories.CourseRepository;
import com.grupoenzo.recommender.core.ports.repositories.EnrollmentRepository;
import com.grupoenzo.recommender.core.embeddings.EmbeddingProvider;
import com.grupoenzo.recommender.core.domain.RecommendedCourse;
import com.grupoenzo.recommender.core.exceptions.CourseNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.UUID;

public class GenerateRank {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EmbeddingProvider embeddingProvider;

    public GenerateRank(CourseRepository courseRepository, 
                       EnrollmentRepository enrollmentRepository,
                       EmbeddingProvider embeddingProvider) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.embeddingProvider = embeddingProvider;
    }

    public List<RecommendedCourse> execute(UUID studentId, UUID completedCourseId, int limit) {
        // 1. Filtrar cursos já cursados
        var enrolledCourses = enrollmentRepository.findByStudentId(studentId);
        var enrolledIds = enrolledCourses.stream()
            .map(e -> e.getCourse().getId())
            .collect(Collectors.toSet());

        // 2. Obter curso completado (para extrair tópicos)
        var completedCourse = courseRepository.findById(completedCourseId)
            .orElseThrow(() -> new CourseNotFoundException());

        // 3. Buscar candidatos (mesma dificuldade ou progressiva)
        var candidates = courseRepository.findAll()
            .stream()
            .filter(c -> !enrolledIds.contains(c.getId()))
            .filter(c -> !c.getId().equals(completedCourseId))
            .limit(limit * 2) // buscar mais que limit para depois rankear
            .collect(Collectors.toList());

        // 4. Rankear por similaridade usando embeddings
        float[] completedEmbedding = embeddingProvider.embed(
            completedCourse.getName() + " " + completedCourse.getDescription()
        );

        return candidates.stream()
            .map(course -> {
                float[] courseEmbedding = embeddingProvider.embed(
                    course.getName() + " " + course.getDescription()
                );
                double similarity = cosineSimilarity(completedEmbedding, courseEmbedding);
                return new RecommendedCourse(course, similarity);
            })
            .sorted(Comparator.comparingDouble(RecommendedCourse::getSimilarity).reversed())
            .limit(limit)
            .collect(Collectors.toList());
    }

    private double cosineSimilarity(float[] a, float[] b) {
        double dotProduct = 0;
        double normA = 0;
        double normB = 0;
        
        for (int i = 0; i < a.length; i++) {
            dotProduct += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
