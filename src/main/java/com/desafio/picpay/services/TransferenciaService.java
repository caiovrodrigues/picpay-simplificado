package com.desafio.picpay.services;

import com.desafio.picpay.services.strategy.TransferenciaValidator;
import com.desafio.picpay.web.domain.Transferencia;
import com.desafio.picpay.web.domain.Usuario;
import com.desafio.picpay.web.dtos.TransferenciaDTO;
import com.desafio.picpay.infrastructure.integration.AutorizadorService;
import com.desafio.picpay.web.repositories.TransferenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransferenciaService {

    private final UsuarioService usuarioService;
    private final AutorizadorService autorizadorService;
    private final TransferenciaRepository transferenciaRepository;
    private final List<TransferenciaValidator> transferenciaValidators;

    public TransferenciaService(UsuarioService usuarioService, AutorizadorService autorizadorService, TransferenciaRepository transferenciaRepository, List<TransferenciaValidator> transferenciaValidators) {
        this.usuarioService = usuarioService;
        this.autorizadorService = autorizadorService;
        this.transferenciaRepository = transferenciaRepository;
        this.transferenciaValidators = transferenciaValidators;
    }

    @Transactional
    public void realizarTransferencia(TransferenciaDTO transferenciaDTO){
        Usuario payer = usuarioService.findById(transferenciaDTO.payer());
        Usuario payee = usuarioService.findById(transferenciaDTO.payee());

        transferenciaValidators.forEach(validacao -> validacao.validar(payer, payee, transferenciaDTO.value()));

        payer.setSaldo(payer.getSaldo() - transferenciaDTO.value());
        payee.setSaldo(payee.getSaldo() + transferenciaDTO.value());

        Transferencia transferencia = Transferencia.builder().payer(payer).payee(payee).valor(transferenciaDTO.value()).build();
        transferenciaRepository.save(transferencia);

        autorizadorService.notificar(transferencia);

    }

}
