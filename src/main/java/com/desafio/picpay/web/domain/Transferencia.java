package com.desafio.picpay.web.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "payer")
    private Usuario payer;

    @ManyToOne
    @JoinColumn(name = "payee")
    private Usuario payee;

    @Column(name = "valor")
    private Double valor;

    @CreationTimestamp
    @Column(name = "realizado_em")
    private LocalDateTime realizadoEm;

}
