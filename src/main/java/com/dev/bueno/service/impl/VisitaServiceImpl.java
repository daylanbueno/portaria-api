package com.dev.bueno.service.impl;

import com.dev.bueno.dto.VisitaDto;
import com.dev.bueno.repository.VisitaRepository;
import com.dev.bueno.service.VisitaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitaServiceImpl implements VisitaService {

    private final VisitaRepository visitaRepository;
    private final ModelMapper modelMapper;


    @Override
    public VisitaDto salvar(VisitaDto visitaDto) {
        return null;
    }
}
