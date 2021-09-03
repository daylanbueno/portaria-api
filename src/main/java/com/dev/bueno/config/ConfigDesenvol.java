package com.dev.bueno.config;

import com.dev.bueno.dto.UsuarioDto;
import com.dev.bueno.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class ConfigDesenvol  implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        UsuarioDto administrador = UsuarioDto.builder().admin(true).email("daylansantos@gmail.com")
                .senha("admin").nome("DAILAN BUENO").build();
        usuarioService.salvar(administrador);
    }
}
