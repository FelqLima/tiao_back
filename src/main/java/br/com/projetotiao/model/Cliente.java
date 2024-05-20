package br.com.projetotiao.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("CLIENTE")
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class Cliente extends User {
}