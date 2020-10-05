package com.proyecto.spring.models.entity;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "Paciente")
@Data
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Paciente")
    private Long id_Paciente;

    private String nombres;

    private String apellido_Paterno;

    private String apellido_Materno;

    private String dni;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_Nacimiento;

    private String correo;

    private String genero;

    private String telefono;
    @Lob
    @Column(name = "foto", length = Integer.MAX_VALUE)
    private byte[] foto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Usuario")
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contextura")
    private Contextura contextura;
}
