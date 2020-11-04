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

    public List<Administrador> findByCorreo(String correo);

    @Query(value = "SELECT  a FROM Administrador a  WHERE  a.usuario.username = ?1")
    public Administrador ObtenerPorUsuario(String user);

    //Seccion de reportes
    @Query(value = "{call sp_reporte_citas_por_estado(?1)}", nativeQuery = true)
    public List ReporteCitasxEstado(String estado);
}
