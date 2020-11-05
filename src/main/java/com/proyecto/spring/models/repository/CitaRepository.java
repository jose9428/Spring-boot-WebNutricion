package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.Cita;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends  JpaRepository<Cita, Long>{
    
    @Query(value = "SELECT  c FROM Cita c  WHERE  c.fecha_cita =?1  and  c.paciente.usuario.username = ?2 and c.estado=?3")
    public List<Cita> ListarCitasPendientesPorPaciente(Date fecha , String usuario,String estado);
    
      @Query(value = "SELECT count(c) FROM Cita c  WHERE  c.estado = ?1")
    public int CantCitasPendientes(String estado);
    
       @Query(value = "SELECT  c FROM Cita c  WHERE  c.fecha_cita =?1  and  c.nutricionista.usuario.username = ?2 and c.estado=?3")
    public List<Cita> ListarCitasPendientesPorNutricionista(Date fecha , String usuario,String estado);

}
