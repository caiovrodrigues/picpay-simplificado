package com.desafio.picpay.services.strategy;

import com.desafio.picpay.web.domain.Usuario;

public interface TransferenciaValidator {

    void validar(Usuario payer,Usuario payee, Double valor);

}
