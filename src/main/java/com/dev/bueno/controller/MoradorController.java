package com.dev.bueno.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/moradores")
public class MoradorController {

    @GetMapping
    public String morador() {
        return "Morador1";
    }
}
