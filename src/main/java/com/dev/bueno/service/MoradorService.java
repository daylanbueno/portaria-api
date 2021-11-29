package com.dev.bueno.service;

import com.dev.bueno.dto.MoradorDto;
import com.dev.bueno.dto.MoradorFiltroDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MoradorService {

    List<MoradorDto> obterMoradores();

    MoradorDto obterPorId(Long id);

    Page<MoradorDto> obterPorFiltro(MoradorFiltroDto filtro, Pageable pageable);

    MoradorDto salvar(MoradorDto eduardo);

    MoradorDto alterar(MoradorDto eduardo);

    void deletarPorId(Long id);
}
