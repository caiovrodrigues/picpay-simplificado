package com.desafio.picpay;

import com.desafio.picpay.web.domain.Usuario;
import com.desafio.picpay.web.domain.enums.TipoUsuario;
import com.desafio.picpay.web.repositories.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@AfterEach
	void afterEachTest(){
		usuarioRepository.deleteAll();
	}

	protected Usuario criarUsuarioComum(Double saldo){
		Usuario usuario = new Usuario(null, "Caio", "12345678900", "caio@example.com", "123", saldo, TipoUsuario.COMUM.getId());
		return usuarioRepository.save(usuario);
	}

	protected Usuario criarUsuarioLojista(Double saldo){
		Usuario usuario = new Usuario(null, "Lara", "12345678901", "lara@example.com", "321", saldo, TipoUsuario.LOJISTA.getId());
		return usuarioRepository.save(usuario);
	}
}
