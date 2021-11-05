package com.dev.bueno.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UsuarioController.class)
public class MoradorController {


    @Test
    @DisplayName("deve ser capaz de cadastrar um novo morador")
    public void deveCadastraNovoMorador() {

    }
}
