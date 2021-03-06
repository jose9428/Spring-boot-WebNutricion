package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Cita;
import java.util.Date;
import java.util.List;

public interface ICitaService {

    public List<Cita> getList();

    public void ReservarCita(Cita c);

    public List<Cita> ListarCitasPendientesPorPaciente(Date fecha, String usuario);

    public int CitasPendientes();

    public List<Cita> ListarCitasPendientesPorNutricionista(Date fecha, String usuario);

    public Cita CitaDetalle(Long idCita, String estado);

    public List<Cita> ListarCitasPorPaciente(String usuario, String estado);

    public List<Cita> ListarCitasPorIdPaciente(Long idPaciente, String estado);
}
