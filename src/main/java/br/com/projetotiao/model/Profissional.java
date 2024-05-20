package br.com.projetotiao.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("PROFISSIONAL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profissional extends User {
	@OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL)
    private Set<ProfissionalProfissao> profissoes = new HashSet<>();
}
