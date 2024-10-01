package com.desafio.picpay.infrastructure.integration.dto;

public record AutorizadorExterno(String status, Data data) {
}

record Data(boolean authorization){}
