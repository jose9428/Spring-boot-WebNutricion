package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Cita;
import com.proyecto.spring.models.repository.CitaRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaServiceImpl implements ICitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<Cita> getList() {
        return citaRepository.findAll();
    }

    @Override
    public void ReservarCita(Cita c) {
        citaRepository.save(c);
    }

    @Override
    public List<Cita> ListarCitasPendientesPorPaciente(Date fecha, String usuario) {
        return citaRepository.ListarCitasPendientesPorPaciente(fecha, usuario , "Pendiente");
    }

    @Override
    public int CitasPendientes() {
        return citaRepository.CantCitasPendientes("Pendiente");
    }

    @Override
    public List<Cita> ListarCitasPendientesPorNutricionista(Date fecha, String usuario) {
        return citaRepository.ListarCitasPendientesPorNutricionista(fecha, usuario , "Pendiente");
    }

    @Override
    public Cita CitaDetalle(Long idCita, String estado) {
        return citaRepository.CitaDetalle(idCita, estado);
    }

    @Override
    public List<Cita> ListarCitasPorPaciente(String usuario, String estado) {
        return citaRepository.ListarCitasPorPaciente(usuario, estado);
    }

    @Override
    public List<Cita> ListarCitasPorIdPaciente(Long idPaciente, String estado) {
        return citaRepository.ListarCitasPorIdPaciente(idPaciente, estado);
    }

}
