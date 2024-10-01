package com.desafio.picpay.infrastructure.integration;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@ConfigurationProperties(prefix = "integration.autorizador")
@Configuration
public class AutorizadorUriProvider {

    private String uri;

    public String autorizadorURI(){
        return uri + "v2/authorize";
    }

    public String notificacaoURI(){
        return uri + "v1/notify";
    }

}
