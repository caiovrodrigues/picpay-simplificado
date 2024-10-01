package com.desafio.picpay.infrastructure.exceptions;

public class SaldoInsuficienteException extends RuntimeException{

    public SaldoInsuficienteException(String message){
        super(message);
    }
}
