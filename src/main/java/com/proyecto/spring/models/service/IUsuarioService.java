package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import java.util.List;

public interface IUsuarioService {

    public List<Usuario> getAll();

    public Usuario getById(Long id);

    public void Guardar(Usuario user);

    public Usuario getByToken(String token);

    public boolean ValidarFechas(Long idUsuario);

    public boolean ExisteUsuario(String user);

    public Usuario getByUsuario(String user);
}
