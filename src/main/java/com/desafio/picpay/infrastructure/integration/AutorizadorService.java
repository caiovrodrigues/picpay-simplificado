package com.desafio.picpay.infrastructure.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class AutorizadorService {

    private final WebClient webClient;
    private final ApiUriProvider apiUriProvider;

    public void consultar(){
        webClient.get().uri(apiUriProvider.autorizadorURI()).retrieve().toBodilessEntity().block();
    }
}
