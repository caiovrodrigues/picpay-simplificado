package com.desafio.picpay.web.domain;

import com.desafio.picpay.web.domain.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class TransferenciaEmailNotificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Transferencia transferencia;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    private Integer tentativas;

    private LocalDateTime enviadoEm;

}
