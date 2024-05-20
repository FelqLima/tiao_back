package br.com.projetotiao.dto;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.lang.Nullable;

public record RegisterRequestDTO(
	String name,
	String email,
	String password,
	LocalDate birthday,
	String phone,
	@Nullable String phone2,
	String cpf,
	@Nullable String bio,
	String type,
	@Nullable Set<ProfissaoDTO> profissoes
) {}