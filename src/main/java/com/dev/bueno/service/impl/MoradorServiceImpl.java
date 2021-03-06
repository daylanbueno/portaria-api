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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoradorServiceImpl implements MoradorService {

    private final MoradorRepository moradorRepository;
    private final ModelMapper modelMapper;

    @Override
    public MoradorDto salvar(MoradorDto moradorDto) {
        Morador entity = modelMapper.map(moradorDto, Morador.class);
        Morador novoMorador = moradorRepository.save(entity);
        return modelMapper.map(novoMorador, MoradorDto.class);
    }

    @Override
    public MoradorDto alterar(MoradorDto moradorDto) {
        if (moradorDto.getId() == null) {
            throw new NegocioException("O ID é obrigatório");
        }

        Morador entity = modelMapper.map(moradorDto, Morador.class);
        Morador moradorAlterado = moradorRepository.save(entity);

        return modelMapper.map(moradorAlterado, MoradorDto.class);
    }

    @Override
    public List<MoradorDto> obterMoradores() {
        List<Morador> moradores = moradorRepository.findAll();

        List<MoradorDto> resultado = moradores.stream()
                .map((item) -> modelMapper.map(item, MoradorDto.class)).collect(Collectors.toList());

        return resultado;
    }

    @Override
    public MoradorDto obterPorId(Long id) {
        Morador entity = moradorRepository
                .findById(id).orElseThrow(() -> new NegocioException("O morador não encontrado!"));
        return modelMapper.map(entity, MoradorDto.class);
    }

    @Override
    public Page<MoradorDto> obterPorFiltro(MoradorFiltroDto filtro, Pageable pageable) {
        Page<Morador> moradores = moradorRepository.findByNome(filtro.getNome(), pageable);

        List<MoradorDto> resultado = moradores.stream().map((entity) -> {
            return modelMapper.map(entity, MoradorDto.class);
        }).collect(Collectors.toList());

        return new PageImpl<MoradorDto>(resultado, pageable, moradores.getTotalElements());
    }

    @Override
    public void deletarPorId(Long id) {
       moradorRepository.findById(id).orElseThrow(
                () -> new NegocioException("Não existe um morador para o ID informado"));
        moradorRepository.deleteById(id);
    }
}
