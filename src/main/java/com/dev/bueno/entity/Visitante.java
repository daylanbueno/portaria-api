package com.dev.bueno.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@Entity
public class Visitante {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String rg;
    private String imagem;
}
