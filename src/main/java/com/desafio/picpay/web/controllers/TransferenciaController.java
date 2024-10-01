package com.desafio.picpay.web.controllers;

import com.desafio.picpay.web.dtos.TransferenciaDTO;
import com.desafio.picpay.services.TransferenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/transfer")
@RestController
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @PostMapping
    public ResponseEntity<Void> transferencia(@RequestBody TransferenciaDTO transferencia){
        transferenciaService.realizarTransferencia(transferencia);
        return ResponseEntity.ok().build();
    }

}
