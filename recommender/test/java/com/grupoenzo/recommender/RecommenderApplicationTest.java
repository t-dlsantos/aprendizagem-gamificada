package com.grupoenzo.recommender;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RecommenderApplicationTest {

    @Test
    void testInstantiation() {
        assertDoesNotThrow(() -> new RecommenderApplication());
    }

    @Test
    void testMain() {
        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class);
             MockedStatic<Dotenv> dotenvStatic = mockStatic(Dotenv.class)) {
             
            springApplication.when(() -> SpringApplication.run(eq(RecommenderApplication.class), any(String[].class)))
                    .thenReturn(mock(ConfigurableApplicationContext.class));

            // Mock Dotenv
            DotenvBuilder builder = mock(DotenvBuilder.class);
            Dotenv dotenv = mock(Dotenv.class);
            DotenvEntry entry = mock(DotenvEntry.class);
            
            when(entry.getKey()).thenReturn("TEST_KEY_12345");
            when(entry.getValue()).thenReturn("TEST_VALUE");
            
            dotenvStatic.when(Dotenv::configure).thenReturn(builder);
            when(builder.directory(anyString())).thenReturn(builder);
            when(builder.ignoreIfMissing()).thenReturn(builder);
            when(builder.load()).thenReturn(dotenv);
            when(dotenv.entries()).thenReturn(Set.of(entry));

            RecommenderApplication.main(new String[]{});

            springApplication.verify(() -> SpringApplication.run(eq(RecommenderApplication.class), any(String[].class)));
        }
    }

    @Test
    void testMainWithDotenvException() {
        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class);
             MockedStatic<Dotenv> dotenvStatic = mockStatic(Dotenv.class)) {
             
            springApplication.when(() -> SpringApplication.run(eq(RecommenderApplication.class), any(String[].class)))
                    .thenReturn(mock(ConfigurableApplicationContext.class));

            // Mock Dotenv to throw exception
            dotenvStatic.when(Dotenv::configure).thenThrow(new RuntimeException("Dotenv error"));

            assertDoesNotThrow(() -> RecommenderApplication.main(new String[]{}));

            springApplication.verify(() -> SpringApplication.run(eq(RecommenderApplication.class), any(String[].class)));
        }
    }
}
