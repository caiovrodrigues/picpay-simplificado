package com.desafio.picpay.infrastructure.integration;

import com.desafio.picpay.web.domain.Transferencia;
import com.desafio.picpay.web.domain.TransferenciaNotificacao;
import com.desafio.picpay.web.domain.enums.EmailStatus;
import com.desafio.picpay.web.repositories.TransferenciaEmailNotificacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificacaoService {

    private final int MAX_RETRIES = 3;
    private final int SECONDS_WAIT_BEFORE_RETRY = 3;
    private final WebClient webClient;
    private final ApiUriProvider apiUriProvider;
    private final TaskScheduler taskScheduler;
    private final TransferenciaEmailNotificacaoRepository emailNotificacaoRepository;

    @Async(value = "asyncNotificacao")
    public void notificar(Transferencia transferencia){
        log.info("MÉTODO notificar() sendo executado pela thread -> " + Thread.currentThread().getName());
        URI uri = UriComponentsBuilder.fromHttpUrl(apiUriProvider.notificacaoURI()).build().toUri();

        var emailNotificacao = TransferenciaNotificacao.builder().transferencia(transferencia).build();

        tentarNotificarAndSalvar(emailNotificacao, uri, Instant.now(), 1);
    }

    private void tentarNotificarAndSalvar(TransferenciaNotificacao emailNotificacao, URI uri, Instant instant, int tentativa) {
        log.info("MÉTODO tentarNotificar() sendo executado pela thread -> " + Thread.currentThread().getName());
        if(tentativa > MAX_RETRIES){
            emailNotificacao.setStatus(EmailStatus.NOT_SENT);
            emailNotificacao.setTentativas(tentativa - 1);
            emailNotificacao.setEnviadoEm(null);
            emailNotificacaoRepository.save(emailNotificacao);
            log.info("TENTATIVAS MÁXIMA ATINGIDA, NÃO FOI POSSÍVEL NOTIFICAR.");
            return;
        }
        taskScheduler.schedule(() -> {
            try {
                webClient.post().uri(uri).retrieve().toBodilessEntity().block();
                emailNotificacao.setTentativas(tentativa);
                emailNotificacao.setStatus(EmailStatus.SENT);
                emailNotificacao.setEnviadoEm(LocalDateTime.now());
                emailNotificacaoRepository.save(emailNotificacao);
                log.info("USUÁRIO NOTIFICADO COM SUCESSO NA {}° TENTATIVA: ", tentativa);
            } catch (Exception e) {
                log.info("TENTATIVA DE NOTIFICAÇÃO FALHOU. Tentativa: {}; Tentando novamente em: {} segundos...", tentativa, SECONDS_WAIT_BEFORE_RETRY);
                tentarNotificarAndSalvar(emailNotificacao, uri, instant.plusSeconds(SECONDS_WAIT_BEFORE_RETRY), tentativa + 1);
            }
        }, instant);
    }
}
