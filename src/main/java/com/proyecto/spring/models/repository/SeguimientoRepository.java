package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {

    @Query(value = "SELECT  c FROM Seguimiento c  WHERE  c.cita.id_Cita = ?1")
    public Seguimiento getById(Long idCita);
}
