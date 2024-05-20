package br.com.projetotiao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projetotiao.model.Profissao;

@Repository
public interface ProfissaoRepository extends JpaRepository<Profissao, String> {

	Profissao findByNome(String profissaoNome);

}
