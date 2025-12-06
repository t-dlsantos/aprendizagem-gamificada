package com.grupoenzo.recommender.infra.llm;

import com.grupoenzo.recommender.core.ports.llm.LlmClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class DummyLlmClient implements LlmClient {
    @Override
    public String generate(String prompt) {
        return "Recomendado: com base no contexto, este curso parece uma boa escolha.";
    }
}
