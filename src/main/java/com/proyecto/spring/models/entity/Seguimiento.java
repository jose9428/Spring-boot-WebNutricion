package com.proyecto.spring.models.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "seguimiento")
@Data
public class Seguimiento {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    private Long id_seguimiento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Cita")
    private Cita cita;

    private String hora_Desayuno;
    private String hora_Media_Mañana;
    private String hora_Almuerzo;
    private String hora_Cena;
    private String desayuno;
    private String media_Mañana;
    private String almuerzo;
    private String cena;
    private String recomendacion;

    public String hrsManiana() {
        return hora_Media_Mañana;
    }

    public String descManiana() {
        return media_Mañana;
    }
}
