package com.dev.bueno.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitanteDto {
    private Long id;
    private String nome;
    private String rg;
    private String imagemBase64;
}
