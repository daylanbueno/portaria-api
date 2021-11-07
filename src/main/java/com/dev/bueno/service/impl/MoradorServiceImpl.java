package com.dev.bueno.service.impl;

import com.dev.bueno.dto.MoradorDto;
import com.dev.bueno.dto.MoradorFiltroDto;
import com.dev.bueno.entity.Morador;
import com.dev.bueno.exceptions.NegocioException;
import com.dev.bueno.repository.MoradorRepository;
import com.dev.bueno.service.MoradorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoradorServiceImpl implements MoradorService {

    private final  MoradorRepository moradorRepository;
    private final ModelMapper modelMapper;

    @Override
    public MoradorDto salvar(MoradorDto moradorDto) {
        Morador entity = modelMapper.map(moradorDto, Morador.class);
        Morador novoMorador = moradorRepository.save(entity);
        return modelMapper.map(novoMorador, MoradorDto.class);
    }

    @Override
    public List<MoradorDto> obterMoradores() {
        return null;
    }

    @Override
    public MoradorDto obterPorId(Long id) {
        Morador entity = moradorRepository
                .findById(id).orElseThrow(() -> new NegocioException("O morador não encontrado!"));
        return modelMapper.map(entity, MoradorDto.class);
    }

    @Override
    public Page<MoradorDto> obterPorFiltro(MoradorFiltroDto build) {
        return null;
    }

    @Override
    public MoradorDto alterar(MoradorDto eduardo) {
        return null;
    }

    @Override
    public void deletarPorId(Long id) {

    }
}
