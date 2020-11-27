package com.proyecto.spring.models.entity;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "Antropometrico")
@Data
public class Antropometrico {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Antropometrico")
    private Long id_Antropometrico;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Cita")
    private Cita cita;

    @Temporal(TemporalType.DATE)
    private Date fecha_atencion;

    private int altura;

    private int peso;
    
    private int cintura;
    
    private int cuello;
    
    private int cadera;

    private String estado;

    private double imc;

    private String condicion;

    private double grasa_corporal;

    private double masa_corporal;

    private int calorias;
}
