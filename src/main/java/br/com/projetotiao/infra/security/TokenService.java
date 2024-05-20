package br.com.projetotiao.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.projetotiao.model.User;

@Service
public class TokenService {
	
	// String de segurança para criação do token
	// Por motivos de segurança, utilizar 
	//		variáveis de ambiente no application.properties
	@Value("$api.security.token.secret")
	private String secret;
	

	// Geração do token
	public String generateToken(User user) {
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			String token = JWT.create()
						.withIssuer("projetotiao")
						.withSubject(user.getEmail())
						.withExpiresAt(generateExpirationDate())
						.sign(algorithm);
			
			return token;
			
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error while authenticating");
		}
	}

	// Método de validação do token
	public String validadeToken(String token) {
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			return JWT.require(algorithm)
					.withIssuer("projetotiao")
					.build()
					.verify(token)
					.getSubject();
			
		} catch (JWTVerificationException e) {
			
			// Vai cair no outro método como erro de autenticação.
			return null;
			
		}
	}
	
	// Método de geração do tempo de expiração do token
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.ofHours(-3));
	}
	
}
