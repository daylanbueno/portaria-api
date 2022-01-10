package com.dev.bueno.controller;

import com.dev.bueno.dto.VisitaDto;
import com.dev.bueno.service.VisitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visitas")
@RequiredArgsConstructor
public class VisitaController {

    private final VisitaService visitaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitaDto salvar(@RequestBody VisitaDto visitaDto) {
        return visitaService.salvar(visitaDto);
    }

}
