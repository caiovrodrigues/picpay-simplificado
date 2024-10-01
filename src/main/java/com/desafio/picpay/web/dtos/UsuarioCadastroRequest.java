package com.desafio.picpay.web.dtos;

public record UsuarioCadastroRequest(
        String nome,
        String cpf,
        String email,
        String senha,
        String saldo,
        Integer tipoUsuario
) {
}
