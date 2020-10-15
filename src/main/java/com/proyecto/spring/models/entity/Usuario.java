package com.proyecto.spring.models.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Usuario")
@Data
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Usuario")
    private Long id_Usuario;

    @NotEmpty(message = "El campo del usuario es requerido")
    @Column(name = "username")
    private String username;
    @NotEmpty(message = "El campo de la contraseña es requerido")
    @Column(name = "pass")
    private String pass;

    @Column(name = "estado")
    private int estado;

    private String token;

    @Column(name = "fecha_Registro")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_Registro;

    @OneToOne
    @JoinColumn(name = "id_Perfil")
    private Perfil perfil;

}
