package br.com.projetotiao.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProfissionalProfissaoId implements Serializable {
    @Column(name = "profissional_id")
    private Long profissionalId;
    
    @Column(name = "profissao_id")
    private Long profissaoId;
}