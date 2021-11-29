package com.dev.bueno.service;

import com.dev.bueno.dto.MoradorDto;
import com.dev.bueno.dto.MoradorFiltroDto;
import com.dev.bueno.entity.Morador;
import com.dev.bueno.exceptions.NegocioException;
import com.dev.bueno.repository.MoradorRepository;
import com.dev.bueno.service.impl.MoradorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class MoradorServiceTest {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private MoradorRepository moradorRepository;

    private MoradorService moradorService;


    @BeforeEach
    public void setUp() {
        moradorService = new MoradorServiceImpl(
                moradorRepository,
                modelMapper
        );
    }

    @Test
    @DisplayName("deve cadastrar novo morador com sucesso")
    public void deveCadastrarNovoMorador() {
        Morador entity = Morador.builder()
                .nome("Marcos").celular("99666655").id(10l).endereco("B11").build();

        MoradorDto moradoDto = MoradorDto.builder()
                .nome("Marcos").celular("99666655").id(10l).endereco("B11").build();

        Mockito.when(moradorRepository.save(entity)).thenReturn(entity);
        Mockito.when(modelMapper.map(moradoDto, Morador.class)).thenReturn(entity);
        Mockito.when(modelMapper.map(entity, MoradorDto.class)).thenReturn(moradoDto);

        MoradorDto novoMorador = moradorService.salvar(moradoDto);

        Assertions.assertEquals(novoMorador.getNome(), entity.getNome());
        Assertions.assertEquals(novoMorador.getId(), entity.getId());
    }

    @Test
    @DisplayName("deve alterar  morador com sucesso")
    public void deveAlterarMorador() {
        Morador entity = Morador.builder()
                .nome("Marcos").celular("99666655").id(10l).endereco("B11").build();

        MoradorDto moradoDto = MoradorDto.builder()
                .nome("Marcos da Silva").celular("619995588").id(10l).endereco("B19").build();

        Mockito.when(moradorRepository.save(entity)).thenReturn(entity);
        Mockito.when(modelMapper.map(moradoDto, Morador.class)).thenReturn(entity);
        Mockito.when(modelMapper.map(entity, MoradorDto.class)).thenReturn(moradoDto);

        MoradorDto novoMorador = moradorService.alterar(moradoDto);

        Assertions.assertEquals(novoMorador.getNome(), moradoDto.getNome());
        Assertions.assertEquals(novoMorador.getId(), moradoDto.getId());
    }

    @Test
    @DisplayName("deve tentar alterar morador sem passa ID")
    public void deveAlterarMoradorSemId() {
        MoradorDto moradoDto = MoradorDto.builder()
                .nome("Marcos da Silva").celular("619995588").endereco("B19").build();
        Assertions.assertThrows(NegocioException.class, () -> moradorService.alterar(moradoDto));
    }

    @Test
    @DisplayName("deve ser capaz de obter os morador por ID quando existir")
    public void deveSerCapazDeObterMoradoresPorId() {
        Morador entity = Morador.builder()
                .nome("Marcos").celular("99666655").id(10l).endereco("B11").build();

        MoradorDto moradoDto = MoradorDto.builder()
                .nome("Marcos").celular("99666655").id(10l).endereco("B11").build();

        Mockito.when(moradorRepository.findById(10l)).thenReturn(Optional.of(entity));
        Mockito.when(modelMapper.map(entity, MoradorDto.class)).thenReturn(moradoDto);

        MoradorDto novoMorador = moradorService.obterPorId(10l);

        Assertions.assertEquals(novoMorador.getNome(), entity.getNome());
        Assertions.assertEquals(novoMorador.getId(), entity.getId());
    }

    @Test
    @DisplayName("deve ser capaz de carregar os moradores")
    public void deveSerCapazDeCarregarMoradoes() {
        Morador entity = Morador.builder()
                .nome("Marcos").celular("99666655").id(10l).endereco("B11").build();

        MoradorDto moradoDto = MoradorDto.builder()
                .nome("Marcos").celular("99666655").id(10l).endereco("B11").build();

        Mockito.when(moradorRepository.findAll()).thenReturn(
                Arrays.asList(entity)
        );

        Mockito.when(modelMapper.map(entity, MoradorDto.class)).thenReturn(moradoDto);

        List<MoradorDto> moradores = moradorService.obterMoradores();

        Assertions.assertEquals(1, moradores.size());
    }

    @Test
    @DisplayName("deve ser capaz de obter morador por ID quando não existir")
    public void deveSerCapazDeObterMoradoresPorIdELancaException() {
        Assertions.assertThrows(NegocioException.class, () -> moradorService.obterPorId(10l));
    }

    @Test
    @DisplayName("deve ser capaz de obter morador por filtro")
    public void deveObterMoradorPorFiltro() {
        Pageable pageable = PageRequest.of(1,10);
        Morador morador1 = Morador.builder().nome("DAILAN SILVA LIMA").id(10l).endereco("B11").build();
        MoradorDto moradorDto = MoradorDto.builder().nome("DAILAN SILVA LIMA").id(10l).endereco("B11").build();
        List<Morador> moradores = Arrays.asList(morador1);

        MoradorFiltroDto filtroDto = MoradorFiltroDto.builder().nome("dailan").build();

        Mockito.when(moradorRepository.findByNome(filtroDto.getNome(), pageable))
                .thenReturn(new PageImpl<Morador>(moradores, pageable, moradores.size()));

        Mockito.when(modelMapper.map(morador1,MoradorDto.class)).thenReturn(moradorDto);

        Page<MoradorDto> resultado = moradorService.obterPorFiltro(filtroDto, pageable);

        Assertions.assertEquals(1, resultado.getContent().size());
        Assertions.assertEquals(10l, resultado.getContent().get(0).getId());
        Assertions.assertEquals(moradores.get(0).getEndereco(), resultado.getContent().get(0).getEndereco());

    }
}
