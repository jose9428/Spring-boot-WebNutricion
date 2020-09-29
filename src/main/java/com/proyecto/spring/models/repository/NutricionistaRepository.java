package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.Nutricionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NutricionistaRepository extends JpaRepository<Nutricionista, Long> {

    @Query(value = "{call SP_CODIGO_NUTRICIONISTA()}", nativeQuery = true)
    public String UsuarioGenerado();

    @Query(value = "SELECT  n.usuario.id_Usuario FROM  Nutricionista n WHERE  n.id_Nutricionista=?1")
    public Long ObtenerIdUsuario(Long idNutricionista);
    
}
