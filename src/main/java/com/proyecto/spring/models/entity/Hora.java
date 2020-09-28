package com.proyecto.spring.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Hora")
@Data
public class Hora {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hora")
    private Long id_hora;

    private String hora_Inicio;

    private String hora_Fin;

    @ManyToOne
    @JoinColumn(name = "id_Turno")
    private Turno turno;

}
