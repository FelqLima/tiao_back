package br.com.projetotiao.dto;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.lang.Nullable;

public record UserResponseDTO (   
		String name,
		String email,
		LocalDate birthday,
		String phone,
		@Nullable String phone2,
		String cpf,
		@Nullable String bio,
		@Nullable Set<ProfissaoDTO> profissoes) {}
