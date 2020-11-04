package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    public Usuario findByToken(String token);
    
    @Query(value = "{call sp_validar_fecha_cambio(?)}" , nativeQuery = true)
    public long DiferenciaFechasCambio(Long idUsuario);
    
    public Usuario findByUsername(String user);

}
