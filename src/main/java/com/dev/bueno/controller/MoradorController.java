package com.dev.bueno.controller;

import com.dev.bueno.dto.MoradorDto;
import com.dev.bueno.dto.MoradorFiltroDto;
import com.dev.bueno.service.MoradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moradores")
@RequiredArgsConstructor
public class MoradorController {

    private final MoradorService moradorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MoradorDto salvar(@RequestBody MoradorDto moradorDto) {
        return moradorService.salvar(moradorDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MoradorDto> moradores() {
        return moradorService.obterMoradores();
    }

    @GetMapping("/{id}")
    public MoradorDto obterPorId(@PathVariable Long id) {
        return moradorService.obterPorId(id);
    }


    @GetMapping("/filtro")
    public Page<MoradorDto> obterPorFiltro(MoradorFiltroDto filtroDto) {
        return moradorService.obterPorFiltro(filtroDto);
    }
}
