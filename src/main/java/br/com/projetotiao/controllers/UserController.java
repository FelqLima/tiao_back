package br.com.projetotiao.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetotiao.dto.ProfissaoDTO;
import br.com.projetotiao.dto.UserRequestDTO;
import br.com.projetotiao.dto.UserResponseDTO;
import br.com.projetotiao.model.Cliente;
import br.com.projetotiao.model.Profissional;
import br.com.projetotiao.model.ProfissionalProfissao;
import br.com.projetotiao.model.User;
import br.com.projetotiao.repositories.ClienteRepository;
import br.com.projetotiao.repositories.ProfissionalRepository;
import br.com.projetotiao.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserRepository repository;
	private final ProfissionalRepository profissionalRepository;
	//private final ClienteRepository clienteRepository;
	
	@GetMapping("/profissional")
	public ResponseEntity<List<UserResponseDTO>> getAllProfissionais() {
	    List<Profissional> profissionais = profissionalRepository.findAll();
	    List<UserResponseDTO> profissionalDTOs = profissionais.stream()
	            .map(this::buildUserDTO)
	            .collect(Collectors.toList());
	    return ResponseEntity.ok(profissionalDTOs);
	}
	
	@PostMapping("/profile")
	public ResponseEntity<?> getUser(@RequestBody UserRequestDTO body) {
		
		UserResponseDTO userResponseDTO = null;
		Optional<User> user = this.repository.findByEmail(body.email());
		
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		// Montando o retorno
		userResponseDTO = buildUserDTO(user.get());
	   
		if (userResponseDTO != null) {
			return ResponseEntity.ok(userResponseDTO);
		} else {
			return ResponseEntity.badRequest().body("Erro na montagem da resposta!");
		}
	}

	private UserResponseDTO buildUserDTO(User user) {
		
		if (user instanceof Profissional) { 
			
			Set<ProfissaoDTO> profissoesDTO = new HashSet<>();
	        for (ProfissionalProfissao profissionalProfissao : ((Profissional) user).getProfissoes()) {
	            ProfissaoDTO profissaoDTO = new ProfissaoDTO(
	            		profissionalProfissao.getProfissao().getNome(),
	            		profissionalProfissao.getDataInicio());
	            profissoesDTO.add(profissaoDTO);
	        }
			
			return new UserResponseDTO(
					user.getName(), 
					user.getEmail(), 
					user.getBirthday(), 
					user.getPhone(), 
					user.getPhone2(),
					user.getCpf(),
					user.getBio(),
					profissoesDTO
					);
			
		} else if (user instanceof Cliente) {
			
			return new UserResponseDTO(
					user.getName(), 
					user.getEmail(), 
					user.getBirthday(), 
					user.getPhone(), 
					user.getPhone2(),
					user.getCpf(),
					user.getBio(),
					null);
		}
		
		return null;
	}
}
