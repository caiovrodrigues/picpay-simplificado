package com.desafio.picpay.services.strategy;

import com.desafio.picpay.infrastructure.exceptions.Forbidden;
import com.desafio.picpay.web.domain.Usuario;
import com.desafio.picpay.web.domain.enums.TipoUsuario;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class TipoUsuarioStrategy implements TransferenciaValidator{
    @Override
    public void validar(Usuario payer, Usuario payee, Double valor) {
        if (TipoUsuario.LOJISTA.getId().equals(payer.getTipoUsuario())) {
            throw new Forbidden("Lojista não pode realizar transferência.");
        }
    }
}
