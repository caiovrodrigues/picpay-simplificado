package com.desafio.picpay.infrastructure.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(Forbidden.class)
    public ResponseEntity<ErrorMessage> unauthorizedException(Forbidden e, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(Instant.now(), HttpStatus.FORBIDDEN.value(), e.getMessage(), request.getServletPath()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(Instant.now(), HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getServletPath()));
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<ErrorMessage> saldoInsuficiente(SaldoInsuficienteException e, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), request.getServletPath()));
    }

}
