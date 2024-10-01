package com.desafio.picpay.infrastructure.exceptions;

import java.time.Instant;

public record ErrorMessage(
    Instant timestamp,
    Integer status,
    String message,
    String path
) {
}
