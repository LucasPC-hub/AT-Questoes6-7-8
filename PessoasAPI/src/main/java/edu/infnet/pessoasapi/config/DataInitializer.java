package edu.infnet.pessoasapi.config;

import edu.infnet.pessoasapi.model.Pessoa;
import edu.infnet.pessoasapi.repo.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public void run(String... args) throws Exception {
        pessoaRepository.save(new Pessoa(null, "Pessoa 1")).block();
        pessoaRepository.save(new Pessoa(null, "Pessoa 2")).block();
        pessoaRepository.save(new Pessoa(null, "Pessoa 3")).block();
        System.out.println("3 pessoas criadas");
    }
}