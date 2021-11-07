package com.dev.bueno.service.impl;

import com.dev.bueno.dto.MoradorDto;
import com.dev.bueno.dto.MoradorFiltroDto;
import com.dev.bueno.service.MoradorService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoradorServiceImpl implements MoradorService {
    @Override
    public List<MoradorDto> obterMoradores() {
        return null;
    }

    @Override
    public MoradorDto obterPorId(Long id) {
        return null;
    }

    @Override
    public Page<MoradorDto> obterPorFiltro(MoradorFiltroDto build) {
        return null;
    }

    @Override
    public MoradorDto salvar(MoradorDto eduardo) {
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
