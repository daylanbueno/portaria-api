package com.dev.bueno.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Morador {
    private Long id;
    private String nome;
    private String endereco;
    private String enderecoCompleto;
    private String celular;
    private String telefone;
}
