package com.proyecto.spring.models.entity;

public class Reporte {

    private int nroMes;
    private int cantidad;
    private String nombreMes;

    public Reporte() {
    }

    public void NombreMes() {
        String mes[] = {"", "Enero", "Febrero", "Marzo", "Abril",
            "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        setNombreMes(mes[getNroMes()]);
    }

    @Override
    public String toString() {
        return "Reporte{" + "nroMes=" + nroMes + ", cantidad=" + cantidad + '}';
    }

    public int getNroMes() {
        return nroMes;
    }

    public void setNroMes(int nroMes) {
        this.nroMes = nroMes;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreMes() {
        return nombreMes;
    }

    public void setNombreMes(String nombreMes) {
        this.nombreMes = nombreMes;
    }

}
