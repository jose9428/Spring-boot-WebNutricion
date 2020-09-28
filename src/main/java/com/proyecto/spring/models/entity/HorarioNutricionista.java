package com.proyecto.spring.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Horario_Nutricionista")
@Data
public class HorarioNutricionista {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Horario")
    private Long id_Horario;

    @ManyToOne
    @JoinColumn(name = "id_Turno")
    private Turno turno;

    @ManyToOne
    @JoinColumn(name = "id_Nutricionista")
    private Nutricionista nutricionista;
}
