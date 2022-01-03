package com.dev.bueno.service.impl;

import com.dev.bueno.dto.VisitanteDto;
import com.dev.bueno.dto.VisitanteFiltroDto;
import com.dev.bueno.service.VisitanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitanteServiceImpl implements VisitanteService {

    @Override
    public VisitanteDto salvar(VisitanteDto visitanteDto) {
        return null;
    }

    @Override
    public VisitanteDto obterPorRg(String id) {
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
