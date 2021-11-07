package com.dev.bueno.controller;

import com.dev.bueno.Specification;
import com.dev.bueno.dto.MoradorDto;
import com.dev.bueno.dto.MoradorFiltroDto;
import com.dev.bueno.service.MoradorService;
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
import org.springframework.data.domain.Pageable;
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
@WebMvcTest(MoradorController.class)
public class MoradorControllerTest  extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MoradorService moradorService;


    @Test
    @DisplayName("deve ser capaz de cadastrar um novo morador")
    public void deveCadastrarNovoMorador() throws Exception {

        MoradorDto eduardo = MoradorDto.builder()
                .nome("Dailan")
                .endereco("Conjunto B lote 18")
                .celular("6199999999")
                .build();

        MoradorDto novoMorador = MoradorDto.builder()
                .id(10l)
                .nome("Dailan")
                .endereco("Conjunto B lote 18")
                .celular("6199999999")
                .build();

        Mockito.when(moradorService.salvar(eduardo)).thenReturn(novoMorador);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/moradores")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(eduardo));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(novoMorador.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(novoMorador.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("endereco").value(novoMorador.getEndereco()))
                .andExpect(MockMvcResultMatchers.jsonPath("celular").value(novoMorador.getCelular()))
                .andReturn();
    }

    @Test
    @DisplayName("deve ser capaz de obter uma lista de moradores")
    public void deveObterTodosOsMoradores() throws Exception {

        MoradorDto eduardo = MoradorDto.builder()
                .nome("Dailan")
                .endereco("Conjunto B lote 18")
                .celular("6199999999")
                .build();

        Mockito.when(moradorService.obterMoradores()).thenReturn(Arrays.asList(eduardo));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/moradores")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].nome").value(eduardo.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].endereco").value(eduardo.getEndereco()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].celular").value(eduardo.getCelular()))
                .andReturn();
    }

    @Test
    @DisplayName("deve obter  morador dado o seu ID")
    public void deveObterMoradorPorId() throws Exception {
        MoradorDto marcos = MoradorDto.builder().nome("MARCOS").id(10l).build();
        Mockito.when(moradorService.obterPorId(10l)).thenReturn(marcos);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/moradores/10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result  = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(marcos.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(marcos.getNome()))
                .andReturn();
    }

    @Test
    @DisplayName("deve obter moradores por filtro e consulta paginada quando retorna uma pessoa")
    public void deveObterMoradoresPorFiltroConsultaPaginadaDevolveMarcos() throws Exception {

        MoradorDto marcos = MoradorDto.builder().nome("MARCOS").id(10l).build();

        Mockito.when(moradorService.obterPorFiltro(MoradorFiltroDto.builder().build())).thenReturn(new PageImpl<>(
                    Arrays.asList(marcos), PageRequest.of(1, 10), 10));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/moradores/filtro")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result  = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].id").value(marcos.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].nome").value(marcos.getNome()))
                .andReturn();
    }


}
