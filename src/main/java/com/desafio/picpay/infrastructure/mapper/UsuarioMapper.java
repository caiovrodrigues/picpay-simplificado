package com.desafio.picpay.infrastructure.mapper;

import com.desafio.picpay.web.domain.Usuario;
import com.desafio.picpay.web.dtos.UsuarioCadastroRequest;
import com.desafio.picpay.web.dtos.UsuarioResponseDTO;
import com.desafio.picpay.web.domain.enums.TipoUsuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {

    UsuarioMapper MAPPER = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(defaultValue = "2000", target = "saldo")
    @Mapping(defaultExpression = "java(retornaIdTipoUsuario(dto.tipoUsuario()))", target = "tipoUsuario")
    Usuario usuarioDtoToUsuario(UsuarioCadastroRequest dto);

    UsuarioResponseDTO usuarioToResponseDTO(Usuario usuario);

    default TipoUsuario retornaTipoUsuario(Integer tipoUsuario){
        return TipoUsuario.findTipoUsuarioById(tipoUsuario);
    }

    default Integer retornaIdTipoUsuario(Integer tipoUsuario){
        return retornaTipoUsuario(tipoUsuario).getId();
    }

}
