package br.com.processo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.processo.entity.Movimentacao;

@Repository
public interface MovimetacaoRepository extends JpaRepository<Movimentacao, Long> {

}
