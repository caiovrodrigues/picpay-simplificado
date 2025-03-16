package com.desafio.picpay.services.strategy.impl;

import com.desafio.picpay.BaseTest;
import com.desafio.picpay.infrastructure.exceptions.Forbidden;
import com.desafio.picpay.infrastructure.integration.AutorizadorService;
import com.desafio.picpay.infrastructure.integration.AutorizadorUriProvider;
import com.desafio.picpay.web.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorizadorValidatorTest extends BaseTest {

    @MockBean
    private WebClient webClient;

    @Autowired
    private AutorizadorUriProvider autorizadorUriProvider;

    @Autowired
    private AutorizadorService autorizadorService;

    @Autowired
    private AutorizadorValidator validator;

    @Test
    @DisplayName("deve lançar exceção se api do autorizador retornar erro")
    void deveLancarExcecaoSeAutorizadorNegar() {
        //given
        Usuario payer = criarUsuarioComum(1000.0);
        Usuario payee = criarUsuarioLojista(1000.0);

        var requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        var requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        var responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(autorizadorUriProvider.autorizadorURI())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.toBodilessEntity()).thenReturn(Mono.error(Exception::new));

        //when-then
        assertThrows(Forbidden.class, () -> validator.validar(payer, payee, 100.0));
    }

    @Test
    @DisplayName("não deve lançar exceção se api do autorizador retornar sucesso")
    void deveValidarSeAutorizadorPermitir(){
        //given
        Usuario payer = criarUsuarioComum(1000.0);
        Usuario payee = criarUsuarioLojista(1000.0);

        var requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(autorizadorUriProvider.autorizadorURI())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity()).thenReturn(Mono.just(ResponseEntity.ok().build()));

        //when-then
        assertDoesNotThrow(() -> validator.validar(payer, payee, 100.0));
    }
























}