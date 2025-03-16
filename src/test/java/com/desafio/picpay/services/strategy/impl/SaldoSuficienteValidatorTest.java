package com.desafio.picpay.services.strategy.impl;

import com.desafio.picpay.BaseTest;
import com.desafio.picpay.infrastructure.exceptions.SaldoInsuficienteException;
import com.desafio.picpay.web.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SaldoSuficienteValidatorTest extends BaseTest {

    @Autowired
    private SaldoSuficienteValidator validator;

    @Test
    @DisplayName("não deve lançar exceção se saldo do pagador for suficiente")
    void deveValidarSeSaldoForSuficiente() {
        //given
        Double valorTransferencia = 500.0;
        Usuario payer = criarUsuarioComum(1_000.0);
        Usuario payee = criarUsuarioLojista(1_000.0);

        //when-then
        assertDoesNotThrow(() -> validator.validar(payer, payee, valorTransferencia));
    }

    @Test
    @DisplayName("deve lançar exceção se saldo do pagador for insuficiente")
    void deveLancarExcecaoSeSaldoForInsuficiente() {
        //given
        Double valorTransferencia = 1_000.0;
        Usuario payer = criarUsuarioComum(500.0);
        Usuario payee = criarUsuarioLojista(500.0);

        //when-then
        assertThrows(SaldoInsuficienteException.class, () -> validator.validar(payer, payee, valorTransferencia));
    }
}