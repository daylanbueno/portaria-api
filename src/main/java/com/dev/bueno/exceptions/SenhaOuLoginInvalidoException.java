package com.dev.bueno.exceptions;

public class SenhaOuLoginInvalidoException extends RuntimeException {
    public SenhaOuLoginInvalidoException() {
        super("Email ou senha invalida.");
    }
}
