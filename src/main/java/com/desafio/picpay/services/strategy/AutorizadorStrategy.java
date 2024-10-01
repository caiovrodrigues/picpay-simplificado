package com.desafio.picpay.services.strategy;

import com.desafio.picpay.infrastructure.exceptions.Forbidden;
import com.desafio.picpay.infrastructure.integration.AutorizadorService;
import com.desafio.picpay.web.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(3)
public class AutorizadorStrategy implements TransferenciaValidator{

    private final AutorizadorService autorizadorService;

    @Override
    public void validar(Usuario payer, Usuario payee, Double valor) {
        try{
            autorizadorService.consultar();
        }catch (Exception e){
            throw new Forbidden("Transferência não autorizada");
        }
    }
}
