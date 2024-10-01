package com.desafio.picpay.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientBean(){
        return WebClient.builder()
                .defaultHeaders((headers) -> headers.put("chave", List.of("valor", "valor")))
                .build();
    }

}
