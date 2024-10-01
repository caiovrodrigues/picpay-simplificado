package com.desafio.picpay.web.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum TipoUsuario {
    COMUM(1),
    LOJISTA(2);

    private Integer id;

    TipoUsuario(int id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static TipoUsuario findTipoUsuarioById(Integer id){
        TipoUsuario[] values = TipoUsuario.values();
        Optional<TipoUsuario> tipoUsuario = Arrays.stream(values).filter(tipo -> tipo.id.equals(id)).findFirst();
        return tipoUsuario.orElse(TipoUsuario.COMUM);
    }

}
