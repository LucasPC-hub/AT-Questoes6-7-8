package edu.infnet.consumirapi.client;

import edu.infnet.consumirapi.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
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
    public static GenericContainer<?> apiContainer = new GenericContainer<>("pessoasapi:latest")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/pessoas").forStatusCode(200));

    @DynamicPropertySource
    static void registerApiProperties(DynamicPropertyRegistry registry) {
        registry.add("api.url", () -> "http://" + apiContainer.getHost() + ":" + apiContainer.getFirstMappedPort());
    }

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testGetPessoas() {
        Flux<Pessoa> pessoas = pessoaClient.getPessoas();

        StepVerifier.create(pessoas)
                .expectNextMatches(pessoa -> pessoa.getNome().equals("Pessoa 1"))
                .expectNextMatches(pessoa -> pessoa.getNome().equals("Pessoa 2"))
                .expectNextMatches(pessoa -> pessoa.getNome().equals("Pessoa 3"))
                .verifyComplete();
    }
}