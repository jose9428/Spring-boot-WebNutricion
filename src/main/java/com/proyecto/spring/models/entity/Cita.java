package com.proyecto.spring.models.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "Cita")
@Data
public class Cita {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Cita")
    private Long id_Cita;

    @ManyToOne
    @JoinColumn(name = "id_Paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_Nutricionista")
    private Nutricionista nutricionista;

    @ManyToOne
    @JoinColumn(name = "id_hora")
    private Hora hora;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_registro;

    @Temporal(TemporalType.DATE)
    private Date fecha_cita;

    private String estado;

    public String FechaConv() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = null;

        try {
            fecha = df.format(getFecha_cita());
        } catch (Exception ex) {
        }

        return fecha;
    }
}
