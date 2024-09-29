package edu.infnet.consumirapi.client;

import com.exemplo.model.Pessoa;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Testcontainers
@SpringBootTest
public class PessoaClientTest {

    @Autowired
    private PessoaClient pessoaClient;

    @Container
    public static GenericContainer<?> apiContainer = new GenericContainer<>("api-image:latest")
            .withExposedPorts(8080);

    @DynamicPropertySource
    static void registerApiProperties(DynamicPropertyRegistry registry) {
        registry.add("api.url", () -> "http://" + apiContainer.getHost() + ":" + apiContainer.getFirstMappedPort());
    }

    @Test
    public void testGetPessoas() {
        Flux<Pessoa> pessoas = pessoaClient.getPessoas();

        StepVerifier.create(pessoas)
                .expectNextMatches(pessoa -> pessoa.getId() != null && pessoa.getNome() != null)
                .verifyComplete();
    }
}