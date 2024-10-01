package com.desafio.picpay.infrastructure.integration;

import com.desafio.picpay.infrastructure.integration.dto.AutorizadorExterno;
import com.desafio.picpay.web.domain.Transferencia;
import com.desafio.picpay.web.domain.TransferenciaEmailNotificacao;
import com.desafio.picpay.web.domain.enums.EmailStatus;
import com.desafio.picpay.web.repositories.TransferenciaEmailNotificacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class AutorizadorService {

    private final Integer MAX_RETRIES = 3;
    private final Integer SECONDS_WAIT_BEFORE_RETRY = 3;
    private final WebClient webClient;
    private final AutorizadorUriProvider autorizadorUriProvider;
    private final TaskScheduler taskScheduler;
    private final TransferenciaEmailNotificacaoRepository emailNotificacaoRepository;

    public AutorizadorService(WebClient webClient, AutorizadorUriProvider autorizadorUriProvider, TaskScheduler taskScheduler, TransferenciaEmailNotificacaoRepository emailNotificacaoRepository) {
        this.webClient = webClient;
        this.autorizadorUriProvider = autorizadorUriProvider;
        this.taskScheduler = taskScheduler;
        this.emailNotificacaoRepository = emailNotificacaoRepository;
    }

    public AutorizadorExterno consultar(){
        return webClient.get().uri(autorizadorUriProvider.autorizadorURI()).retrieve().bodyToMono(AutorizadorExterno.class).block();
    }

    @Async(value = "asyncNotificacao")
    public void notificar(Transferencia transferencia){
        log.info("MÉTODO notificar() sendo executado pela thread -> " + Thread.currentThread().getName());
        URI uri = UriComponentsBuilder.fromHttpUrl(autorizadorUriProvider.notificacaoURI()).build().toUri();

        var emailNotificacao = TransferenciaEmailNotificacao.builder().transferencia(transferencia).build();

        tentarNotificarAndSalvar(emailNotificacao, uri, Instant.now(), 1);
    }

    private void tentarNotificarAndSalvar(TransferenciaEmailNotificacao emailNotificacao, URI uri, Instant instant, int tentativa) {
        log.info("MÉTODO tentarNotificar() sendo executado pela thread -> " + Thread.currentThread().getName());
        if(tentativa > MAX_RETRIES){
            emailNotificacao.setStatus(EmailStatus.NOT_SENT);
            emailNotificacao.setTentativas(tentativa);
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
