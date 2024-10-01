package com.desafio.picpay.web.controllers;

import com.desafio.picpay.web.dtos.UsuarioCadastroRequest;
import com.desafio.picpay.web.dtos.UsuarioResponseDTO;
import com.desafio.picpay.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/usuarios")
@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> save(@RequestBody UsuarioCadastroRequest usuario){
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuario));
    }

}
