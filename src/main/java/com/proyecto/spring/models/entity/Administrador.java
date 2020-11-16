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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "Administrador")
@Data
public class Administrador {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Admi")
    private Long id_Admi;

    @NotEmpty(message = "El campo del nombre es requerido")
    @Size(min = 3, max = 50, message = "El campo del nombre debe estar en un rango de 3 a 50 caracteres.")
    private String nombres;
    @NotEmpty(message = "El campo del apellido paterno es requerido")

    private String apellido_Paterno;
    @NotEmpty(message = "El campo del apellido materno es requerido")
    private String apellido_Materno;
    
     @NotEmpty(message = "El campo del dni es requerido")
    private String dni;

    @Temporal(TemporalType.DATE)
    private Date fecha_Nacimiento;

    @Email(message = "El email es obligatorio y debe tener el correspondiente formato.")
    private String correo;

    private String genero;

    @Lob
    @Column(name = "foto", length = Integer.MAX_VALUE)
    private byte[] foto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Usuario")
    private Usuario usuario;
}
