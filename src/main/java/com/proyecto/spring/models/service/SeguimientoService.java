
package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Seguimiento;
import com.proyecto.spring.models.repository.SeguimientoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeguimientoService implements ISeguimientoService{

    @Autowired
    private SeguimientoRepository seguimientoRepository;
    
    @Override
    public List<Seguimiento> getAll() {
        return seguimientoRepository.findAll();
    }

    @Override
    public void Guardar(Seguimiento s) {
        seguimientoRepository.save(s);
    }

    @Override
    public Seguimiento getById(Long idCita) {
        return seguimientoRepository.getById(idCita);
    }
    
}
