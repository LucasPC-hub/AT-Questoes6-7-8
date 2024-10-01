package edu.infnet.consumirapi.client;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import edu.infnet.consumirapi.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@Testcontainers
@SpringBootTest
public class PessoaClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PessoaClientTest.class);

    @Autowired
    private PessoaClient pessoaClient;

    @Container
    public static GenericContainer<?> apiContainer = new GenericContainer<>("pessoasapi:latest")
            .withExposedPorts(8080)
            .withCreateContainerCmdModifier(cmd -> cmd.withPortBindings(new PortBinding(Ports.Binding.bindPort(8080), new ExposedPort(8080))))
            .waitingFor(Wait.forHttp("/pessoas").forStatusCode(200).withStartupTimeout(Duration.ofMinutes(2)))
            .withLogConsumer(new Slf4jLogConsumer(LOGGER));

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