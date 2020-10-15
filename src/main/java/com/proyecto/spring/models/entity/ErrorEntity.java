package com.proyecto.spring.models.entity;

import lombok.Data;

@Data
public class ErrorEntity {

    String campo;
    String mensaje;

    public ErrorEntity() {
    }

    public ErrorEntity(String campo, String mensaje) {
        this.campo = campo;
        this.mensaje = mensaje;
    }

}
