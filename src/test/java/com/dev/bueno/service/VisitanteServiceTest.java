package com.dev.bueno.service;

import com.dev.bueno.dto.VisitanteDto;
import com.dev.bueno.dto.VisitanteFiltroDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
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

    @Test
    @DisplayName("deve ser capaz de obter visitante por filtro quando nao existir")
    public void deveObterVisitantePorFiltroQuandoNaoExistir() {
        Pageable pageable = PageRequest.of(1,10);
        Visitante visitante1 = Visitante.builder().nome("DAILAN SILVA LIMA").id(10l).build();
        VisitanteDto visitanteDto = VisitanteDto.builder().nome("DAILAN SILVA LIMA").id(10l).build();
        List<Visitante> visitantes = Arrays.asList(visitante1);

        VisitanteFiltroDto filtroDto = VisitanteFiltroDto.builder().nome("dailan").build();

        Mockito.when(visitanteRepository.findAll(filtroDto.toSpecification(), pageable))
                .thenReturn(new PageImpl<Visitante>(visitantes, pageable, visitantes.size()));

        Mockito.when(modelMapper.map(visitante1,VisitanteDto.class)).thenReturn(visitanteDto);

        Page<VisitanteDto> resultado = visitanteService.obterPorFiltro(filtroDto, pageable);

        Assertions.assertEquals(0, resultado.getContent().size());

    }

    @Test
    @DisplayName("deve ser capaz de deletar um visitante por ID com sucesso")
    public void deveDeletarUmVisitantePorId() {
        Long id = 10l;
        Visitante eduardo = Visitante.builder().id(10l).nome("Eduardo").build();
        Mockito.when(visitanteRepository.findById(id)).thenReturn(Optional.of(eduardo));
        Mockito.doNothing().when(visitanteRepository).deleteById(id);
        visitanteService.deletePorId(id);
        Mockito.verify(visitanteRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("deve tentar deletar um visitante quando nao existir")
    public void deveDeletarVisitanteQuandoNaoExistir() {
        Long id = 10l;
        Mockito.when(visitanteRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NegocioException.class, () ->  visitanteService.deletePorId(id));
        Mockito.verify(visitanteRepository, Mockito.never()).deleteById(id);
    }


}
