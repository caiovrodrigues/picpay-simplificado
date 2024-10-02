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
public class TransferenciaNotificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "transferencia_id")
    private Transferencia transferencia;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    @Column(name = "tentativas")
    private Integer tentativas;

    @Column(name = "enviado_em")
    private LocalDateTime enviadoEm;

}
