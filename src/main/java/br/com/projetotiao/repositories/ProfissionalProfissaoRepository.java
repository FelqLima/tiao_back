package br.com.projetotiao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projetotiao.model.ProfissionalProfissao;

@Repository
public interface ProfissionalProfissaoRepository extends JpaRepository<ProfissionalProfissao, Long> {

}
