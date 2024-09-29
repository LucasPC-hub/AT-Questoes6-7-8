package edu.infnet.consumirapi.client;

import edu.infnet.consumirapi.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Service
public class PessoaClient {

    @Autowired
    private WebClient webClient;

    @GetMapping("/getpessoas")
    public Flux<Pessoa> getPessoas() {
        return webClient.get()
                .uri("/pessoas")
                .retrieve()
                .bodyToFlux(Pessoa.class);
    }

    @PostMapping("/createpessoa")
    public Mono<Pessoa> createPessoa(Pessoa pessoa) {
        return webClient.post()
                .uri("/pessoas")
                .bodyValue(pessoa)
                .retrieve()
                .bodyToMono(Pessoa.class);
    }

}