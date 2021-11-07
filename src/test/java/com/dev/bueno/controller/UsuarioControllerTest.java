package com.dev.bueno.controller;

import com.dev.bueno.Specification;
import com.dev.bueno.dto.AuthDto;
import com.dev.bueno.dto.LoginDto;
import com.dev.bueno.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    static String URL_USUARIO_API = "/api/usuarios";

    @SneakyThrows
    @Test
    @DisplayName(" deve ser capaz de fazer login")
    public void deveSerCapazDeFazerLogin() {

        AuthDto authentication = AuthDto.builder().email("teste@mail.com").token("xxxffss").token("xxxyz").build();
        LoginDto login = LoginDto.builder().senha("1234").email("teste@mail.com").build();

        when(usuarioService.login(login)).thenReturn(authentication);

        RequestBuilder request = MockMvcRequestBuilders.post(URL_USUARIO_API+"/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(login));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("token").value(authentication.getToken()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(authentication.getEmail()))
                .andReturn();
    }
}
