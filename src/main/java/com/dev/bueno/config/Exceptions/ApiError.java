package com.dev.bueno.config.Exceptions;

import lombok.Data;

@Data
public class ApiError {

    private String messagem;

    public ApiError(String message) {
        this.messagem = message;
    }

}
