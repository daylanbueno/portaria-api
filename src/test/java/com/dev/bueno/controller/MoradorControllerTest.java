package com.dev.bueno.controller;

import com.dev.bueno.Specification;
import com.dev.bueno.dto.MoradorDto;
import com.dev.bueno.repository.UsuarioRepository;
import com.dev.bueno.service.MoradorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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
    @DisplayName("deve ser capaz de obter uma lista de moradores")
    public void deveObterTodosOsMoradores() throws Exception {

        MoradorDto eduardo = MoradorDto.builder()
                .nome("Dailan")
                .endereco("Conjunto B lote 18")
                .celular("6199999999")
                .build();

        Mockito.when(moradorService.obterMoradores()).thenReturn(Arrays.asList(eduardo));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/moradores")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].nome").value(eduardo.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].endereco").value(eduardo.getEndereco()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].celular").value(eduardo.getCelular()))
                .andReturn();
    }
}
