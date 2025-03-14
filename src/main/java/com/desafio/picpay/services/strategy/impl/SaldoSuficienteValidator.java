package com.desafio.picpay.services.strategy.impl;

import com.desafio.picpay.infrastructure.exceptions.SaldoInsuficienteException;
import com.desafio.picpay.services.strategy.TransferenciaValidator;
import com.desafio.picpay.web.domain.Usuario;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class SaldoSuficienteValidator implements TransferenciaValidator {

    @Override
    public void validar(Usuario payer, Usuario payee, Double valor) {
        if (payer.getSaldo() < valor)
            throw new SaldoInsuficienteException(payer.getNome() + " não possui saldo suficiente.");
    }
}
