package com.dev.bueno.controller;

import com.dev.bueno.Specification;
import com.dev.bueno.dto.VisitaDto;
import com.dev.bueno.service.VisitaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VisitanteController.class)
public class VisitaControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitaService visitaService;

    @Test
    @DisplayName("deve ser capaz cadastrar uma nova visita")
    public void deveCadastrarUmaNovaVisita() throws Exception {
        VisitaDto visitaEndereco = VisitaDto.builder()
                .idVisitante(123l).idUnidadeVisita(12l).build();

        VisitaDto vistaCadastrada = VisitaDto.builder()
                .idVisitante(123l).idUnidadeVisita(12l).id(10l).build();

        Mockito.when(visitaService.salvar(visitaEndereco)).thenReturn(vistaCadastrada);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/visitas")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(visitaEndereco));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(vistaCadastrada.getId()))
                .andReturn();
    }

}
