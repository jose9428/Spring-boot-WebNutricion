package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INutricionistaService {

    public List<Nutricionista> getAll();

    public Nutricionista getById(Long id);

    public String UsuarioGenerado();

    public void Guardar(Nutricionista n);

    public Long ObtenerIdUsuario(Long id);

    public void ActualizarEstado(Long id, int estado);

    public Page<Nutricionista> getListDisponibles(Pageable pageable);

    public int getCantDisponibles();

    public List<Nutricionista> getListCorreo(String correo);

    public boolean ExisteCorreo(String correo);
    
    public boolean ExisteCorreoNotId(String correo , Long id);
    
    public Nutricionista ObtenerPorUsuario(String user);
}
