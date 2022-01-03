package com.dev.bueno.controller;

import com.dev.bueno.dto.VisitanteDto;
import com.dev.bueno.dto.VisitanteFiltroDto;
import com.dev.bueno.service.VisitanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visitantes")
@RequiredArgsConstructor
public class VisitanteController {

    private final VisitanteService visitanteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitanteDto salvar(@RequestBody VisitanteDto visitanteDto) {
        return visitanteService.salvar(visitanteDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VisitanteDto alterar(@RequestBody VisitanteDto visitanteDto) {
        return visitanteService.alterar(visitanteDto);
    }

    @GetMapping("/{rg}")
    public VisitanteDto obterPorRg(@PathVariable String rg) {
        return visitanteService.obterPorRg(rg);
    }

    @GetMapping("/filtro")
    public Page<VisitanteDto> obterPorFiltro(VisitanteFiltroDto visitanteFiltroDto, Integer page, Integer size) {
        return visitanteService.obterPorFiltro(visitanteFiltroDto, PageRequest.of(page, size));
    }

}
