package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.HorarioNutricionista;
import com.proyecto.spring.models.entity.Nutricionista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioNutricionistaRepository extends JpaRepository<HorarioNutricionista, Long> {

    @Query(value = "SELECT  h.nutricionista FROM HorarioNutricionista h "
            + "  WHERE  h.turno.id_Turno=?1")
    public List<Nutricionista> ListadoNutricionistaPorTurno(Long idTurno);
    
     @Query(value = "{call sp_filtro_medicos_por_horario(:idTurno)}", nativeQuery = true)
     public List ListadoNutricionistaDispTurno(@Param("idTurno")  Long idTurno);
     
}
