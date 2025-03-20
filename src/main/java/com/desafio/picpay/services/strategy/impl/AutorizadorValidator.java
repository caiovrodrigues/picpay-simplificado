package com.desafio.picpay.services.strategy.impl;

import com.desafio.picpay.infrastructure.exceptions.Forbidden;
import com.desafio.picpay.infrastructure.integration.AutorizadorService;
import com.desafio.picpay.services.strategy.TransferenciaValidator;
import com.desafio.picpay.web.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
@Order(3)
public class AutorizadorValidator implements TransferenciaValidator {

    private final AutorizadorService autorizadorService;

    @Override
    public void validar(Usuario payer, Usuario payee, Double valor) {
        try{
            autorizadorService.consultar();
        }catch (Exception e){
            log.info(
                    "Transfer failed: Payer {} - Payee {} - Value {}; Cause: {}",
                    payer.getNome(),
                    payee.getNome(),
                    valor,
                    "Transferência não autorizada");
            throw new Forbidden("Transferência não autorizada");
        }
    }
}
