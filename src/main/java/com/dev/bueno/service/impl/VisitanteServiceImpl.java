package com.dev.bueno.service.impl;

import com.dev.bueno.dto.VisitanteDto;
import com.dev.bueno.dto.VisitanteFiltroDto;
import com.dev.bueno.entity.Visitante;
import com.dev.bueno.exceptions.NegocioException;
import com.dev.bueno.repository.VisitanteRepository;
import com.dev.bueno.service.VisitanteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitanteServiceImpl implements VisitanteService {

    private final VisitanteRepository visitanteRepository;
    private final ModelMapper modelMapper;

    @Override
    public VisitanteDto salvar(VisitanteDto visitanteDto) {
        Visitante visitanteEntity = modelMapper.map(visitanteDto, Visitante.class);
        Visitante novoVisitante = visitanteRepository.save(visitanteEntity);
        return modelMapper.map(novoVisitante, VisitanteDto.class);
    }

    @Override
    public VisitanteDto alterar(VisitanteDto visitanteDto) {
        visitanteRepository.findById(visitanteDto.getId()).orElseThrow(() ->
                new NegocioException("Visitante com ID informado não existe"));

        Visitante visitanteEntity  = modelMapper.map(visitanteDto, Visitante.class);

        Visitante visitanteAlterado = visitanteRepository.save(visitanteEntity);
        return modelMapper.map(visitanteAlterado, VisitanteDto.class);
    }

    @Override
    public VisitanteDto obterPorRg(String rg) {
        Visitante visitante = visitanteRepository.findByRg(rg);
        if (visitante == null) throw new NegocioException("Visitante não encontrado");
        return modelMapper.map(visitante, VisitanteDto.class);
    }

    @Override
    public Page<VisitanteDto> obterPorFiltro(VisitanteFiltroDto visitanteFiltroDto, Pageable pageable) {
        Page<Visitante> resultado =
                visitanteRepository.findAll(visitanteFiltroDto.toSpecification(), pageable);

        if (resultado == null) return  new PageImpl<VisitanteDto>(new ArrayList<>(), Pageable.unpaged(), 0);

        List<VisitanteDto> visitantes = resultado.getContent().stream()
                .map((entity) -> modelMapper.map(entity, VisitanteDto.class))
                .collect(Collectors.toList());

        return new PageImpl<VisitanteDto>(visitantes, pageable, resultado.getTotalElements());
    }

    @Override
    public void deletePorId(Long id) {
        Visitante visitante = visitanteRepository.findById(id).orElseThrow(()
                -> new NegocioException("Visitante para o ID não encontrado"));
        visitanteRepository.deleteById(visitante.getId());
    }

}
