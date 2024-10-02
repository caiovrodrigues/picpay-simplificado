package com.desafio.picpay.web.repositories;

import com.desafio.picpay.web.domain.TransferenciaNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaEmailNotificacaoRepository extends JpaRepository<TransferenciaNotificacao, Integer> {
}
