package com.desafio.picpay.infrastructure.exceptions;

public class Forbidden extends RuntimeException{

    public Forbidden(String message){
        super(message);
    }
}
