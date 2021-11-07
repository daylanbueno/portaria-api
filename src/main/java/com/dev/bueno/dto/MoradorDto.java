package com.dev.bueno.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoradorDto {
    private String nome;
    private String endereco;
    private String enderecoCompleto;
    private String celular;
    private String telefone;
}
