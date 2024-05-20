package br.com.projetotiao.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profissional_profissao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalProfissao {
	
	@EmbeddedId
    private ProfissionalProfissaoId id;
    
    @ManyToOne
    @JoinColumn(name = "profissional_id", insertable = false, updatable = false)
    private Profissional profissional;
    
    @ManyToOne
    @JoinColumn(name = "profissao_id", insertable = false, updatable = false)
    private Profissao profissao;
    
    @Column(name = "data_inicio")
    private LocalDate dataInicio;
}