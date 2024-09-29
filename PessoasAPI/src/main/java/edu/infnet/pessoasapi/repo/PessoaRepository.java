package edu.infnet.pessoasapi.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends ReactiveCrudRepository<Pessoa, Long> {
}