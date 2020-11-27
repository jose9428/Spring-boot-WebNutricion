package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.Alimentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentosRepository extends JpaRepository<Alimentos, Long> {

    @Query(value = "SELECT  count(a) FROM  Alimentos a WHERE  a.nombre_Alimento=?1")
    public int ContarAlimentosPorNombre(String nombre);
    
    @Query(value = "SELECT  count(a) FROM  Alimentos a WHERE  a.nombre_Alimento=?1 and a.id_Alimento !=?2")
    public int ContarAlimentosPorNombreYId( String nombre , Long id);
}
