package com.dev.bueno.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDto {
    private Long idUsuario;
    private String nome;
    private String email;
    private String token;
}
