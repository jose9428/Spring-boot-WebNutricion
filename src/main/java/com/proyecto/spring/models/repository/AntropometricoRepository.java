package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.Antropometrico;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AntropometricoRepository extends JpaRepository<Antropometrico, Long> {

    @Query(value = "SELECT  c FROM Antropometrico c  WHERE  c.cita.paciente.id_Paciente =?1 ORDER BY fecha_atencion desc")
    public List<Antropometrico> ListarPorPaciente(Long idPaciente);
}
