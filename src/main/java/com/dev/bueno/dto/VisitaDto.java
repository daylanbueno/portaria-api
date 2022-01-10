package com.dev.bueno.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitaDto {
    private Long id;
    private Long idVisitante;
    private Long idUnidadeVisita;
    private LocalDate dataHoraVisita;
    private String dataHoraFormatada;
}
