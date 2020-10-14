package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.Nutricionista;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NutricionistaRepository extends JpaRepository<Nutricionista, Long> {

    @Query(value = "{call SP_CODIGO_NUTRICIONISTA()}", nativeQuery = true)
    public String UsuarioGenerado();

    @Query(value = "SELECT  n.usuario.id_Usuario FROM  Nutricionista n WHERE  n.id_Nutricionista=?1")
    public Long ObtenerIdUsuario(Long idNutricionista);

    @Query(value = "SELECT  n FROM Nutricionista n WHERE  n.usuario.estado = 1")
    public Page<Nutricionista> ListadoNutricionistaDisponibles(Pageable pageable);

    @Query(value = "SELECT count(n) FROM Nutricionista n  WHERE  n.usuario.estado = 1")
    public int CantNutricionistaDisponibles();

}
