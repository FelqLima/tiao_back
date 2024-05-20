package br.com.projetotiao.services;

import org.springframework.stereotype.Service;

import br.com.projetotiao.model.Cliente;
import br.com.projetotiao.model.Profissional;
import br.com.projetotiao.model.User;
import br.com.projetotiao.util.UserType;

@Service
public class UserService {
    
    public User createUser(String userType) {
        switch (userType) {
            case UserType.CLIENTE:
                return new Cliente();
            case UserType.PROFISSIONAL:
                return new Profissional();
            default:
                throw new IllegalArgumentException("Tipo de usuário inválido: " + userType);
        }
    }
}