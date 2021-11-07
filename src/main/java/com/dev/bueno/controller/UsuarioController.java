package com.dev.bueno.controller;

import com.dev.bueno.dto.AuthDto;
import com.dev.bueno.dto.LoginDto;
import com.dev.bueno.dto.UsuarioDto;
import com.dev.bueno.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public UsuarioDto salvar(@RequestBody @Valid UsuarioDto usuarioDto) {
        return usuarioService.salvar(usuarioDto);
    }

    @PostMapping("/login")
    public AuthDto salvar(@RequestBody @Valid LoginDto loginDto) {
        return usuarioService.login(loginDto);
    }
}
