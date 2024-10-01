package com.desafio.picpay.infrastructure;

import com.desafio.picpay.web.domain.Usuario;
import com.desafio.picpay.web.domain.enums.TipoUsuario;
import com.desafio.picpay.web.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DatabaseLoaderFake {

    private final UsuarioRepository usuarioRepository;

    @PostConstruct
    public void loadDatabase(){
        Integer usuarioComumId = TipoUsuario.COMUM.getId();
        Integer usuarioLojistaId = TipoUsuario.LOJISTA.getId();

        Usuario comum = new Usuario(null, "Caio", "123.456.789-96", "caio@gmail.com", "123", 2000.0, usuarioComumId);
        Usuario lojista = new Usuario(null, "Lara", "321.654.978-00", "lara@gmail.com", "123", 2000.0, usuarioLojistaId);

        usuarioRepository.save(comum);
        usuarioRepository.save(lojista);
    }

}
