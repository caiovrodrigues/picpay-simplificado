package com.desafio.picpay.web.dtos;

import com.desafio.picpay.web.domain.enums.TipoUsuario;

public record UsuarioResponseDTO(
        String nome,
        String cpf,
        String email,
        String saldo,
        TipoUsuario tipoUsuario
) {
}
