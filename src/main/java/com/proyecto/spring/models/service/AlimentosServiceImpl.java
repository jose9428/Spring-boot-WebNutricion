package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Alimentos;
import com.proyecto.spring.models.repository.AlimentosRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlimentosServiceImpl implements IAlimentosService{
    
    @Autowired
    private AlimentosRepository alimentosRepository;

    @Override
    public void GuardarAlimentos(Alimentos a) {
        alimentosRepository.save(a);
    }

    @Override
    public List<Alimentos> getAll() {
        return alimentosRepository.findAll();
    }

    @Override
    public Alimentos BuscarPorId(Long id) {
        return alimentosRepository.findById(id).orElse(null);
    }

    @Override
    public int CantidadPorNombre(String nombre) {
        return alimentosRepository.ContarAlimentosPorNombre(nombre);
    }

    @Override
    public int CantidadPorNombreYId(String nombre , Long id) {
        return alimentosRepository.ContarAlimentosPorNombreYId(nombre, id);
    }
    
}
