package com.dev.bueno.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Morador {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String endereco;
    private String enderecoCompleto;
    private String celular;
    private String telefone;
}
