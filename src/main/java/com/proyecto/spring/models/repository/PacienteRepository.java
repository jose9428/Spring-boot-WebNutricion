package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query(value = "SELECT count(p) FROM Paciente p  WHERE  p.usuario.estado = 1")
    public int CantPacientesDisponibles();
    
      public List<Paciente> findByCorreo(String correo);
}
