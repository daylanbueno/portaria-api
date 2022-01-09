package com.dev.bueno.service;

import com.dev.bueno.dto.VisitanteDto;
import com.dev.bueno.entity.Visitante;
import com.dev.bueno.exceptions.NegocioException;
import com.dev.bueno.repository.VisitanteRepository;
import com.dev.bueno.service.impl.VisitanteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class VisitanteServiceTest {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private VisitanteRepository visitanteRepository;

    private VisitanteService visitanteService;

    @BeforeEach
    public void setUp() {
        visitanteService = new VisitanteServiceImpl(
                visitanteRepository,
                modelMapper
        );
    }

    @Test
    @DisplayName("deve cadastrar novo visitante com sucesso")
    public void deveCadastrarNovoVisitante() {
        Visitante edu = Visitante
                .builder().nome("Eduardo").id(10l)
                .rg("12345").build();

        VisitanteDto visitanteDto = VisitanteDto.builder()
                        .nome("Eduardo").rg("12345").build();

        VisitanteDto novoVisitanteDto = VisitanteDto.builder()
                .nome("Eduardo").rg("12345").id(10l).build();


        Mockito.when(visitanteRepository.save(edu)).thenReturn(edu);
        Mockito.when(modelMapper.map(visitanteDto, Visitante.class)).thenReturn(edu);
        Mockito.when(modelMapper.map(edu, VisitanteDto.class)).thenReturn(novoVisitanteDto);

        VisitanteDto novoVisitante = visitanteService.salvar(visitanteDto);

        Assertions.assertEquals(edu.getId(), novoVisitante.getId());
        Assertions.assertEquals(edu.getNome(), novoVisitante.getNome());
    }

    @Test
    @DisplayName("deve ser capaz de alterar um visitante com sucesso")
    public void deveSerCapazDeAlterarUmVisitanteComSucesso() {
        Visitante edu = Visitante
                .builder().nome("Eduardo").id(10l)
                .rg("12345").build();

        VisitanteDto visitanteDto = VisitanteDto.builder()
                .nome("Eduardo").rg("12345").id(10l).build();

        VisitanteDto novoVisitanteDto = VisitanteDto.builder()
                .nome("Eduardo Martins").rg("12345").id(10l).build();


        Mockito.when(visitanteRepository.save(edu)).thenReturn(edu);
        Mockito.when(visitanteRepository.findById(10l)).thenReturn(Optional.of(edu));
        Mockito.when(modelMapper.map(visitanteDto, Visitante.class)).thenReturn(edu);
        Mockito.when(modelMapper.map(edu, VisitanteDto.class)).thenReturn(novoVisitanteDto);

        VisitanteDto visitanteAlterado = visitanteService.alterar(visitanteDto);

        Assertions.assertEquals(novoVisitanteDto.getId(), visitanteAlterado.getId());
        Assertions.assertEquals(novoVisitanteDto.getNome(), visitanteAlterado.getNome());
    }

    @Test
    @DisplayName("deve tentar alterar um visitante que nao existe")
    public void alterarVisitanteNaoExistente() {

        VisitanteDto eduDTO = VisitanteDto.builder()
                .nome("Eduardo").rg("12345").id(10l).build();

        Mockito.when(visitanteRepository.findById(10l)).thenReturn(Optional.empty());
        Assertions.assertThrows(NegocioException.class, () ->  visitanteService.alterar(eduDTO));

    }

    @Test
    @DisplayName("deve obter visitante por RG")
    public void deveObterVisitantePorRg() {

        Visitante edu = Visitante
                .builder().nome("Eduardo").id(10l)
                .rg("12345").build();

        VisitanteDto eduDTO = VisitanteDto.builder()
                .nome("Eduardo").rg("12345").id(10l).build();

        Mockito.when(visitanteRepository.findByRg("12345")).thenReturn(edu);
        Mockito.when(modelMapper.map(edu, VisitanteDto.class)).thenReturn(eduDTO);

        VisitanteDto visitanteDto = visitanteService.obterPorRg("12345");

        Assertions.assertEquals(edu.getId(), visitanteDto.getId());
        Assertions.assertEquals(edu.getNome(), visitanteDto.getNome());
    }


    @Test
    @DisplayName("deve obter visitante por RG quando nao existir")
    public void deveTentarObterVisitantePorIdQuandoNaoExistir() {

        VisitanteDto eduDTO = VisitanteDto.builder()
                .nome("Eduardo").rg("12345").id(10l).build();

        Mockito.when(visitanteRepository.findByRg("12345")).thenReturn(null);
        Assertions.assertThrows(NegocioException.class, () ->  visitanteService.obterPorRg("199999"));
    }




}
