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
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "Paciente")
@Data
public class Paciente {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Paciente")
    private Long id_Paciente;
    @NotEmpty(message = "El campo del nombre es requerido")
    private String nombres;
    @NotEmpty(message = "El campo del apellido Paterno es requerido")
    private String apellido_Paterno;
    @NotEmpty(message = "El campo del apellido Materno es requerido")
    private String apellido_Materno;

    @NotEmpty(message = "El campo del dni es requerido")
    @Size(min = 8, max = 8, message = "El campo del dni debe tener 8 digitos numericos")
    private String dni;

    @Temporal(TemporalType.DATE)
    private Date fecha_Nacimiento;

    @Email(message = "El correo electronico debe tener el formato correspondiente")
    @NotEmpty(message = "El campo del correo electronico es requerido")
    private String correo;

    private String genero;

    private String telefono;

    @Lob
    @Column(name = "foto", length = Integer.MAX_VALUE)
    private byte[] foto;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Usuario")
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contextura")
    private Contextura contextura;
}
