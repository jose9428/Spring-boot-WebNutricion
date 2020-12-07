package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.Hora;
import com.proyecto.spring.models.entity.Nutricionista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraRepository extends JpaRepository<Hora, Long> {

    @Query(value = "{call sp_horarios_disponibles(:idTurno , :idMedico , :fecha)}", nativeQuery = true)
    public List<Hora> ListadoHorariosDisp(@Param("idTurno") Long idTurno,
            @Param("idMedico") Long idMedico, @Param("fecha") String fecha);

    @Query(value = "SELECT  n FROM Hora n  WHERE  n.id_hora = ?1")
    public Hora getById(Long idHora);
}
