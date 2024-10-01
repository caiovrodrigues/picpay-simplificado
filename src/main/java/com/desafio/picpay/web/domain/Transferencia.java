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
    private Usuario payer;

    @ManyToOne
    private Usuario payee;

    private Double valor;

    @CreationTimestamp
    private LocalDateTime realizadoEm;

}
