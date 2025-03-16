package com.desafio.picpay.services;

import com.desafio.picpay.BaseTest;
import com.desafio.picpay.web.domain.Usuario;
import com.desafio.picpay.web.domain.enums.TipoUsuario;
import com.desafio.picpay.web.dtos.UsuarioCadastroRequest;
import com.desafio.picpay.web.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UsuarioServiceTest extends BaseTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Test
    @DisplayName("deve salvar usuário")
    void deveSalvarUsuario() {
        //given
        var usuarioRequest = new UsuarioCadastroRequest("Caio", "12345678900", "caio@example.com", "123", "1000.0", TipoUsuario.COMUM.getId());

        //when
        usuarioService.salvarUsuario(usuarioRequest);

        //then
        Usuario usuarioSalvo = usuarioRepository.findAll().stream().findFirst().get();
        Assertions.assertNotNull(usuarioSalvo);

        Assertions.assertEquals(usuarioRequest.nome(), usuarioSalvo.getNome());
        Assertions.assertEquals(usuarioRequest.cpf(), usuarioSalvo.getCpf());
        Assertions.assertEquals(usuarioRequest.email(), usuarioSalvo.getEmail());
        Assertions.assertEquals(Double.parseDouble(usuarioRequest.saldo()), usuarioSalvo.getSaldo());
        Assertions.assertEquals(usuarioRequest.tipoUsuario(), usuarioSalvo.getTipoUsuario());
    }

    @Test
    @DisplayName("deve retornar o usuário quando existir ao buscar por id")
    void deveEncontrarUsuarioPeloIdQuandoExistir() {
        //given
        Usuario usuario = criarUsuarioComum(1000.0);

        //when
        Usuario usuarioEncontrado = usuarioService.findById(usuario.getId());

        //then
        Assertions.assertEquals(usuario, usuarioEncontrado);
    }

    @Test
    @DisplayName("deve lançar exceção ao buscar usuário inexistente pelo id")
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        //given
        int idUsuarioInexistente = 999;

        //when-then
        Assertions.assertThrows(EntityNotFoundException.class, () -> usuarioService.findById(idUsuarioInexistente));
    }
}