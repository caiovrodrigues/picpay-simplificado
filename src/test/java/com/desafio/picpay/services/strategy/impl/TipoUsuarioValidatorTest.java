package com.desafio.picpay.services.strategy.impl;

import com.desafio.picpay.BaseTest;
import com.desafio.picpay.infrastructure.exceptions.Forbidden;
import com.desafio.picpay.web.domain.Usuario;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TipoUsuarioValidatorTest extends BaseTest {

    @Autowired
    private TipoUsuarioValidator validator;

    @Test
    @DisplayName("não deve lançar exceção se o pagador for comum")
    void deveValidarSePagadorForComum() {
        //given
        Usuario payer = criarUsuarioComum(1000.0);
        Usuario payee = criarUsuarioLojista(1000.0);

        //when-then
        assertDoesNotThrow(() -> validator.validar(payer, payee, 100D));
    }

    @Test
    @DisplayName("deve lançar exceção se o pagador for lojista")
    void deveLancarExcecaoSePagadorForLojista(){
        //given
        Usuario payer = criarUsuarioLojista(1000.0);
        Usuario payee = criarUsuarioComum(1000.0);

        //when-then
        assertThrows(Forbidden.class, () -> validator.validar(payer, payee, 100D));
    }
}