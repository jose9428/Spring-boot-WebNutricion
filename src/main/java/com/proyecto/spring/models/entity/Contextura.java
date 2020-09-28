package com.proyecto.spring.models.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contexturas")
@Data
public class Contextura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contextura")
    private Long id_contextura;

    @Column(name = "contextura")
    private String nombreContextura;

    @Column(name = "descripcion")
    private String descripcion;
}
