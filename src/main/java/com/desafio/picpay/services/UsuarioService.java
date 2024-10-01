package com.desafio.picpay.services;

import com.desafio.picpay.web.domain.Usuario;
import com.desafio.picpay.web.dtos.UsuarioCadastroRequest;
import com.desafio.picpay.web.dtos.UsuarioResponseDTO;
import com.desafio.picpay.web.repositories.UsuarioRepository;
import com.desafio.picpay.infrastructure.mapper.UsuarioMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponseDTO salvarUsuario(UsuarioCadastroRequest usuarioDTO) {
        Usuario usuario = UsuarioMapper.MAPPER.usuarioDtoToUsuario(usuarioDTO);
        Usuario usuarioSaved = usuarioRepository.save(usuario);
        return UsuarioMapper.MAPPER.usuarioToResponseDTO(usuarioSaved);
    }

    public Usuario findById(Integer id){
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com id %d não existe.".formatted(id)));
    }
}
