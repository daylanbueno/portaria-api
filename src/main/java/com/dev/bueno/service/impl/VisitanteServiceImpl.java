package com.dev.bueno.service.impl;

import com.dev.bueno.dto.VisitanteDto;
import com.dev.bueno.dto.VisitanteFiltroDto;
import com.dev.bueno.entity.Visitante;
import com.dev.bueno.repository.VisitanteRepository;
import com.dev.bueno.service.VisitanteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public VisitanteDto obterPorRg(String rg) {
        return null;
    }

    @Override
    public Page<VisitanteDto> obterPorFiltro(VisitanteFiltroDto visitanteFiltroDto, Pageable pageable) {
        return null;
    }

    @Override
    public VisitanteDto alterar(VisitanteDto visitanteDto) {
        return null;
    }

    @Override
    public void deletePorId(Long id) {

    }
}
