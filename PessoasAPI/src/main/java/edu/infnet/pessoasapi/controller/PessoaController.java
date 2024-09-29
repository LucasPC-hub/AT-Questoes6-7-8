package edu.infnet.pessoasapi.controller;


import edu.infnet.pessoasapi.model.Pessoa;
import edu.infnet.pessoasapi.repo.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository PessoaRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Pessoa> obterTodasPessoas() {
        return PessoaRepository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Pessoa> criarPessoa(@RequestBody Pessoa pessoa) {
        return PessoaRepository.save(pessoa);
    }
}