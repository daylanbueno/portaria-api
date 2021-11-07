package com.dev.bueno.controller;

import com.dev.bueno.dto.MoradorDto;
import com.dev.bueno.service.MoradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/moradores")
@RequiredArgsConstructor
public class MoradorController {

    private final MoradorService moradorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MoradorDto> moradores() {
        return moradorService.obterMoradores();
    }
}
