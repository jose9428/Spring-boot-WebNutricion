package com.proyecto.spring.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Nutricionista")
@Data
public class Nutricionista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Nutricionista")
    private Long id_Nutricionista;

    private String nombres;

    private String apellido_Paterno;

    private String apellido_Materno;

    private String dni;

    @Temporal(TemporalType.DATE)
    private Date fecha_Nacimiento;

    private String correo;

    private String genero;

    @Lob
    @Column(name = "foto", length = Integer.MAX_VALUE)
    private byte[] foto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Usuario")
    private Usuario usuario;

    public String nomCompletos() {
        return nombres + " " + apellido_Paterno + " " + apellido_Materno;
    }

    public String Cargo() {
        if (genero.equalsIgnoreCase("F")) {
            return "Dra. " + nombres + " " + apellido_Paterno + " " + apellido_Materno;
        } else {
            return "Dr. " + nombres + " " + apellido_Paterno + " " + apellido_Materno;
        }

    }
}
