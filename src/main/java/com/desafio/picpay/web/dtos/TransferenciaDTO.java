package com.desafio.picpay.web.dtos;

public record TransferenciaDTO(
        Double value,
        Integer payer,
        Integer payee
) {
}
