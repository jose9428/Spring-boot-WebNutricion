package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    @Query(value = "{call SP_CODIGO_ADMIN()}", nativeQuery = true)
    public String UsuarioGenerado();
    
         @Query(value = "SELECT  a FROM Administrador a "
            + "  WHERE  a.usuario.estado = 1")
    public List<Administrador> ListadoAdministradoresDisponibles();
    
}
