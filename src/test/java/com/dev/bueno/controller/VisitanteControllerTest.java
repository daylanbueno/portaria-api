package com.dev.bueno.controller;

import com.dev.bueno.Specification;
import com.dev.bueno.dto.VisitanteDto;
import com.dev.bueno.dto.VisitanteFiltroDto;
import com.dev.bueno.service.VisitanteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VisitanteController.class)
public class VisitanteControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitanteService visitanteService;

    @Test
    @DisplayName("deve ser capaz cadastrar um novo visitante")
    public void deveCadastrarNovoMorador() throws Exception {
        VisitanteDto marcos = VisitanteDto.builder().nome("Marcos Silva").build();
        VisitanteDto marcosCadastrado = VisitanteDto.builder().id(10l).nome("Marcos Silva").build();

        Mockito.when(visitanteService.salvar(marcos)).thenReturn(marcosCadastrado);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/visitantes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(marcos));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(marcosCadastrado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(marcosCadastrado.getNome()))
                .andReturn();
    }


    @Test
    @DisplayName("deve ser capaz alterar um visitante")
    public void deveSerCapazDeAlterarUmVisitante() throws Exception {
        VisitanteDto marcos = VisitanteDto.builder().nome("Marcos Silva").build();
        VisitanteDto visitanteAlterado = VisitanteDto.builder().id(10l).nome("Marcos Silva Martins").build();

        Mockito.when(visitanteService.alterar(marcos)).thenReturn(visitanteAlterado);

        RequestBuilder request = MockMvcRequestBuilders.put("/api/visitantes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(marcos));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(visitanteAlterado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(visitanteAlterado.getNome()))
                .andReturn();
    }

    @Test
    @DisplayName("deve obter um visitante por RG")
    public void deveObterVisitantePorId() throws Exception {
        String rg = String.valueOf(123456);
        VisitanteDto marcos = VisitanteDto.builder().id(10l).nome("Marcos Silva").rg(rg).build();

        Mockito.when(visitanteService.obterPorRg(rg)).thenReturn(marcos);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/visitantes/123456")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(marcos));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(marcos.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("rg").value(marcos.getRg()))
                .andReturn();
    }

    @Test
    @DisplayName("deve obter visitantes por nome no filtro dado que existe")
    public void deveObterVisitantePorFiltro() throws Exception {

        VisitanteDto marcos = VisitanteDto.builder().nome("MARCOS").id(10l).rg("123456").build();

        Mockito.when(visitanteService.obterPorFiltro(
                VisitanteFiltroDto.builder().nome("marc").build(),
                PageRequest.of(1, 10)
        )).thenReturn(new PageImpl<>(
                Arrays.asList(marcos), PageRequest.of(1, 10), 10));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/visitantes/filtro?nome=marc&page=1&size=10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result  = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].id").value(marcos.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].nome").value(marcos.getNome()))
                .andReturn();
    }

    @Test
    @DisplayName("deve deletar um visitante dado o seu ID")
    public void deveSerCapazDeDeletarUmMorador() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/visitantes/10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result  = mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();
    }


}
