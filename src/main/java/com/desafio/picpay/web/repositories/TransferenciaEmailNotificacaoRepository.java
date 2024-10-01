package com.desafio.picpay.web.repositories;

import com.desafio.picpay.web.domain.TransferenciaEmailNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaEmailNotificacaoRepository extends JpaRepository<TransferenciaEmailNotificacao, Integer> {
}
