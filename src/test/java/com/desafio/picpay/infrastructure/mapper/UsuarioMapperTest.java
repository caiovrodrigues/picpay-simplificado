package com.desafio.picpay.infrastructure.mapper;

import com.desafio.picpay.BaseTest;
import com.desafio.picpay.web.domain.Usuario;
import com.desafio.picpay.web.domain.enums.TipoUsuario;
import com.desafio.picpay.web.dtos.UsuarioCadastroRequest;
import com.desafio.picpay.web.dtos.UsuarioResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UsuarioMapperTest extends BaseTest {

    @Test
    @DisplayName("deve mapear UsuarioCadastroRequest para Usuario")
    void deveMapearUsuarioCadastroRequestParaUsuario(){
        //given
        var usuarioRequest = new UsuarioCadastroRequest("Caio", "12345678900", "caio@example.com", "123", "1000.0", TipoUsuario.COMUM.getId());

        //when
        Usuario usuario = UsuarioMapper.MAPPER.usuarioDtoToUsuario(usuarioRequest);

        //then
        Assertions.assertEquals(usuarioRequest.nome(), usuario.getNome());
        Assertions.assertEquals(usuarioRequest.cpf(), usuario.getCpf());
        Assertions.assertEquals(usuarioRequest.email(), usuario.getEmail());
        Assertions.assertEquals(Double.parseDouble(usuarioRequest.saldo()), usuario.getSaldo());
        Assertions.assertEquals(usuarioRequest.tipoUsuario(), usuario.getTipoUsuario());
    }

    @Test
    @DisplayName("deve mapear Usuario para UsuarioResponseDTO")
    void deveMapearUsuarioParaUsuarioResponseDTO() {
        //given
        Usuario usuario = criarUsuarioComum(1000.0);

        //when
        UsuarioResponseDTO usuarioResponseDTO = UsuarioMapper.MAPPER.usuarioToResponseDTO(usuario);

        //then
        Assertions.assertEquals(usuarioResponseDTO.nome(), usuario.getNome());
        Assertions.assertEquals(usuarioResponseDTO.cpf(), usuario.getCpf());
        Assertions.assertEquals(usuarioResponseDTO.email(), usuario.getEmail());
        Assertions.assertEquals(usuarioResponseDTO.saldo(), String.valueOf(usuario.getSaldo()));
        Assertions.assertEquals(usuarioResponseDTO.tipoUsuario().getId(), usuario.getTipoUsuario());
    }

    @Test
    @DisplayName("deve retornar TipoUsuario padrão (COMUM) ao buscar por ID inexistente")
    void deveRetornarTipoUsuarioPadraoQuandoIdInexistente() {
        //given
        int idInexistente = 999;

        //when
        TipoUsuario tipoUsuario = UsuarioMapper.MAPPER.retornaTipoUsuario(idInexistente);

        //then
        Assertions.assertEquals(TipoUsuario.COMUM, tipoUsuario);
    }

    @Test
    @DisplayName("deve retornar TipoUsuario com o ID correspondente")
    void deveRetornarTipoUsuarioById() {
        //given
        TipoUsuario lojista = TipoUsuario.LOJISTA;

        //when
        TipoUsuario tipoUsuario = UsuarioMapper.MAPPER.retornaTipoUsuario(lojista.getId());

        //then
        Assertions.assertEquals(lojista, tipoUsuario);
    }

    @Test
    @DisplayName("deve retornar o id do TipoUsuario correspondente")
    void deveRetornarIdByTipoUsuario() {
        //given
        TipoUsuario lojista = TipoUsuario.LOJISTA;

        //when
        Integer idLojista = UsuarioMapper.MAPPER.retornaIdTipoUsuario(lojista.getId());

        //then
        Assertions.assertEquals(lojista.getId(), idLojista);
    }
}