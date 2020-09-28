package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Nutricionista;
import com.proyecto.spring.models.entity.Usuario;
import com.proyecto.spring.models.repository.NutricionistaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NutricionistaServiceImpl implements INutricionistaService {

    @Autowired
    private NutricionistaRepository nutricionistaRepository;

    @Override
    public List<Nutricionista> getAll() {
        return nutricionistaRepository.findAll();
    }

    @Override
    public Nutricionista getById(Long id) {
        return nutricionistaRepository.findById(id).orElse(null);
    }

    @Override
    public String UsuarioGenerado() {
        return nutricionistaRepository.UsuarioGenerado();
    }

    @Override
    public void Guardar(Nutricionista n) {
        nutricionistaRepository.save(n);
    }

    @Override
    public Long ObtenerIdUsuario(Long id) {
        return nutricionistaRepository.ObtenerIdUsuario(id);
    }

    @Override
    public void ActualizarEstado(Long id, int estado) {
        Nutricionista n = getById(id);

        if (n != null) {
            Usuario u = n.getUsuario();
            u.setEstado(estado);
            n.setUsuario(u);
        }
        
        Guardar(n);
    }

}
