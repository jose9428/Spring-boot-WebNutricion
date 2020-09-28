package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.Contextura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContexturaRepository extends JpaRepository<Contextura, Long> {

    @Query(value = "SELECT  count(c) FROM  Contextura c WHERE  c.nombreContextura=?1")
    public int ContarConexturaPorNombre( String nombre);
    
    @Query(value = "SELECT  count(c) FROM  Contextura c WHERE  c.nombreContextura=?1 and id_contextura !=?2")
    public int ContarConexturaPorNombreYId( String nombre , Long id);
    
}
