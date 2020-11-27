package com.proyecto.spring.models.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Alimentos")
@Data
public class Alimentos  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Alimento")
    private Long id_Alimento;

    private String nombre_Alimento;

    private int calorias;

    private int carbohidratos;
    
    private int proteinas;

    private int grasas;

    private int fibra;
}
