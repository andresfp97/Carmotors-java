package com.carmotors.model;

import com.carmotors.model.enums.EstadoServicio;
import com.carmotors.model.enums.TipoMantenimiento;

public class Servicio {
    private int idServicio;
    private TipoMantenimiento tipoMantenimiento;
    private String descripcion;
    private double costoManoObra;
    private EstadoServicio estadoServicio;
    private int tiempoEstimadoMinutos;

    // Constructor
    public Servicio(int idServicio, TipoMantenimiento tipoMantenimiento, String descripcion,
                    double costoManoObra, EstadoServicio estadoServicio, int tiempoEstimado) {
        this.idServicio = idServicio;
        this.tipoMantenimiento = tipoMantenimiento;
        this.descripcion = descripcion;
        this.costoManoObra = costoManoObra;
        this.estadoServicio = estadoServicio;
        this.tiempoEstimadoMinutos = tiempoEstimado;
    }

    public Servicio() {

    }

    // Getters y Setters
    public int getIdServicio() {
        return idServicio;
    }

    public TipoMantenimiento getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    public void setTipoMantenimiento(TipoMantenimiento nombre) {
        this.tipoMantenimiento = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCostoManoObra() {
        return costoManoObra;
    }

    public void setCostoManoObra(double costoManoObra) {
        this.costoManoObra = costoManoObra;
    }

    public EstadoServicio getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(EstadoServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    public int getTiempoEstimadoMinutos() {
        return tiempoEstimadoMinutos;
    }

    public void setTiempoEstimadoMinutos(int tiempoEstimadoMinutos) {
        this.tiempoEstimadoMinutos = tiempoEstimadoMinutos;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "idServicio=" + idServicio +
                ", tipoMantenimiento=" + tipoMantenimiento +
                ", descripcion='" + descripcion + '\'' +
                ", costoManoObra=" + costoManoObra +
                ", estadoServicio=" + estadoServicio +
                ", tiempoEstimadoMinutos=" + tiempoEstimadoMinutos +
                '}';
    }
}