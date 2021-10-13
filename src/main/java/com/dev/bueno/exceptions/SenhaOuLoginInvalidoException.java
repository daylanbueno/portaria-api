package com.dev.bueno.exceptions;

public class SenhaOuLoginInvalidoException extends RuntimeException {
    public SenhaOuLoginInvalidoException() {
        super("O email ou a senha informada não confere.");
    }
}
