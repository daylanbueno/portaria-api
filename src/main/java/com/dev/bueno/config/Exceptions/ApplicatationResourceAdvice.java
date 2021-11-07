package com.dev.bueno.config.Exceptions;

import com.dev.bueno.exceptions.NegocioException;
import com.dev.bueno.exceptions.SenhaOuLoginInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicatationResourceAdvice {

    @ExceptionHandler(NegocioException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ApiError handleNegocioException(NegocioException ex) {
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ApiError handleAccessDeniedException(Exception ex) {
        return new ApiError("Acesso negado!"+ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream().map(erro ->  erro.getDefaultMessage()
                ).collect(Collectors.toList());

        return new ApiError(errors.get(0));
    }

    @ExceptionHandler(SenhaOuLoginInvalidoException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleSenhaOuLoginInvalidoException(SenhaOuLoginInvalidoException ex) {
        return new ApiError(ex.getMessage());
    }

}
