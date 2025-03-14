package com.desafio.picpay.services.strategy.impl;

import com.desafio.picpay.infrastructure.exceptions.Forbidden;
import com.desafio.picpay.web.domain.Usuario;
import com.desafio.picpay.web.domain.enums.TipoUsuario;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioValidatorTest {

    @InjectMocks
    private TipoUsuarioValidator validator;

    @Test
    @DisplayName("não deve lançar exceção se o pagador for comum")
    void deveValidarSePagadorForComum() {
        //given
        Usuario payer = new Usuario(null, "Lara", "12345678901", "lara@example.com", "321", 1000.0, TipoUsuario.COMUM.getId());
        Usuario payee = new Usuario(null, "Caio", "12345678900", "caio@example.com", "123", 1000.0, TipoUsuario.LOJISTA.getId());

        //when-then
        assertDoesNotThrow(() -> validator.validar(payer, payee, 100D));
    }

    @Test
    @DisplayName("deve lançar exceção se o pagador for lojista")
    void deveLancarExcecaoSePagadorForLojista(){
        //given
        Usuario payer = new Usuario(null, "Caio", "12345678900", "caio@example.com", "123", 1000.0, TipoUsuario.LOJISTA.getId());
        Usuario payee = new Usuario(null, "Lara", "12345678901", "lara@example.com", "321", 1000.0, TipoUsuario.COMUM.getId());

        //when-then
        assertThrows(Forbidden.class, () -> validator.validar(payer, payee, 100D));
    }
}