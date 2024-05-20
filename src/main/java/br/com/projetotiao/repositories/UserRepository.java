package br.com.projetotiao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetotiao.model.User;

public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByEmail(String email);
	
}
