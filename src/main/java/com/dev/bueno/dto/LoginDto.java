package com.dev.bueno.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotEmpty(message = "{campo.senha.obrigatorio}")
    private String email;
    @NotEmpty(message = "{campo.email.obrigatorio}")
    private String senha;
    private String token;
}
