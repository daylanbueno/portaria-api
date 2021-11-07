package com.dev.bueno.service;

import com.dev.bueno.dto.AuthDto;
import com.dev.bueno.dto.LoginDto;
import com.dev.bueno.dto.UsuarioDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {

    UsuarioDto salvar(UsuarioDto usuarioDto);

    AuthDto login(LoginDto loginDto);

}
