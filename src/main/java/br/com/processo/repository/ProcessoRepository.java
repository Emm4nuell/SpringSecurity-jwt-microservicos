package br.com.processo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.processo.entity.Processo;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

}
