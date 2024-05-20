package br.com.projetotiao.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetotiao.dto.LoginRequestDTO;
import br.com.projetotiao.dto.ProfissaoDTO;
import br.com.projetotiao.dto.RegisterRequestDTO;
import br.com.projetotiao.dto.RegisterResponseDTO;
import br.com.projetotiao.infra.security.TokenService;
import br.com.projetotiao.model.Cliente;
import br.com.projetotiao.model.Profissao;
import br.com.projetotiao.model.Profissional;
import br.com.projetotiao.model.ProfissionalProfissao;
import br.com.projetotiao.model.ProfissionalProfissaoId;
import br.com.projetotiao.model.User;
import br.com.projetotiao.repositories.ClienteRepository;
import br.com.projetotiao.repositories.ProfissaoRepository;
import br.com.projetotiao.repositories.ProfissionalProfissaoRepository;
import br.com.projetotiao.repositories.ProfissionalRepository;
import br.com.projetotiao.repositories.UserRepository;
import br.com.projetotiao.services.UserService;
import br.com.projetotiao.util.UserType;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
	private UserService userService;
	private final UserRepository repository;
	//private final ProfissionalRepository profissionalRepository;
	//private final ClienteRepository clienteRepository;
	private final ProfissaoRepository profissaoRepository;
	private final ProfissionalProfissaoRepository profissionalProfissaoRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;
	private User newUser = null;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
		
		User user = this.repository.findByEmail(body.email()).orElseThrow(
				() -> new UsernameNotFoundException("User not found"));
		String type = "";
					
		if (passwordEncoder.matches(body.password(), user.getPassword())) {
			String token = this.tokenService.generateToken(user);
			
			if (user instanceof Profissional) {
				type = UserType.PROFISSIONAL;
	        } else if (user instanceof Cliente) {	        	
	        	type = UserType.CLIENTE;
	        }
			return ResponseEntity.ok(new RegisterResponseDTO(user.getName(),type , token));
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
		
		Optional<User> user = this.repository.findByEmail(body.email());
		
		// Se já existir um usuário com esse email...
		if (user.isPresent()) {
			return ResponseEntity.badRequest().body("Email já está em uso.");
		}
		
		// Criando novo usuário que pode ser Profissional ou Cliente
		newUser = userService.createUser(body.type());
		if (newUser == null) {
			return ResponseEntity.badRequest().build();
		}
		
		// // Salvando dados em comum
		newUser.setName(body.name());
		newUser.setEmail(body.email());
		newUser.setBirthday(body.birthday());
		newUser.setPhone(body.phone());
		newUser.setCpf(body.cpf());
		newUser.setBio(body.bio());
		
	    if (StringUtils.hasLength(body.phone2())) {
	        newUser.setPhone2(body.phone2());
	    }
	    if (StringUtils.hasLength(body.bio())) {
	        newUser.setBio(body.bio());
	    }
		
	    // Salvar apenas o Hash da senha no banco.
	    newUser.setPassword(this.passwordEncoder.encode(body.password()));
		
		newUser = this.repository.save(newUser);
		
	    if (newUser instanceof Profissional) {
	    	// Dados específicas no registro do Profissional
	    	Profissional newProfissional = (Profissional) newUser;
	    	addProfissoesToProfissional(newProfissional, body.profissoes());

        } else if (newUser instanceof Cliente) {	        	
        	// Dados específicas no registro do Cliente
        	// Cliente newCliente = (Cliente) newUser;
        }
		
	    String token = this.tokenService.generateToken(newUser);
		return ResponseEntity.ok(new RegisterResponseDTO(newUser.getName(), body.type(), token));
	}	

	private void addProfissoesToProfissional(Profissional profissional, Set<ProfissaoDTO> set) {
	    for (ProfissaoDTO profissaoDTO : set) {
	        Profissao profissao = profissaoRepository.findByNome(profissaoDTO.nome());
	        if (profissao != null) {
	            
	            ProfissionalProfissao profissionalProfissao = new ProfissionalProfissao();
	            profissionalProfissao.setProfissao(profissao);
	            profissionalProfissao.setDataInicio(profissaoDTO.dataInicio());
	            profissionalProfissao.setProfissional(profissional);
	            
	            ProfissionalProfissaoId id = new ProfissionalProfissaoId();
	            id.setProfissionalId(profissional.getId());
	            id.setProfissaoId(profissao.getId());
	            profissionalProfissao.setId(id);
	            
	            profissionalProfissaoRepository.save(profissionalProfissao);
	            
	        } else {
	            System.err.println("Profissão não encontrada: " + profissaoDTO.nome());
	        }
	    }
	}
}
