package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Contextura;
import com.proyecto.spring.models.repository.ContexturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContexturaServiceImpl implements IContexturaService{
    
    @Autowired
    private ContexturaRepository contexturaRepository;

    @Override
    @Transactional
    public void GuardarContextura(Contextura c) {
        contexturaRepository.save(c);
    }

    @Override
    public List<Contextura> getAll() {
        return contexturaRepository.findAll();
    }

    @Override
    public Contextura BuscarPorId(Long id) {
        return contexturaRepository.findById(id).orElse(null);
    }

    @Override
    public int CantidadPorNombre(String nombre) {
        return contexturaRepository.ContarConexturaPorNombre(nombre);
    }

    @Override
    public int CantidadPorNombreYId(String nombre, Long id) {
        return contexturaRepository.ContarConexturaPorNombreYId(nombre, id);
    }
    
}
