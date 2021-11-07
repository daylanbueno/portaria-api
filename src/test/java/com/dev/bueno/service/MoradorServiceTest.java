package com.dev.bueno.service;

import com.dev.bueno.dto.MoradorDto;
import com.dev.bueno.entity.Morador;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}
