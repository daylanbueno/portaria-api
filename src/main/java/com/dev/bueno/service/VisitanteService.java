package com.dev.bueno.service;

import com.dev.bueno.dto.VisitanteDto;
import com.dev.bueno.dto.VisitanteFiltroDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VisitanteService {

    VisitanteDto salvar(VisitanteDto visitanteDto);

    VisitanteDto obterPorRg(String id);

    Page<VisitanteDto> obterPorFiltro(VisitanteFiltroDto visitanteFiltroDto, Pageable pageable);

    VisitanteDto alterar(VisitanteDto visitanteDto);

    void deletePorId(Long id);
}
