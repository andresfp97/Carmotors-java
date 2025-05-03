package com.carmotors.model;

import java.sql.Date;
import java.util.List;

public class Trabajo {
    private Integer idTrabajo;
    private Integer idVehiculo;
    private Integer idServicio;
    private Date fechaRecepcion;
    private Date fechaEntrega;
    private String tecnicoAsignado;
    private List<DetalleTrabajoRepuesto> repuestosUtilizados;

    // Constructor, getters y setters


    public Trabajo() {
    }

    public Integer getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(Integer idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setTecnicoAsignado(String tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public List<DetalleTrabajoRepuesto> getRepuestosUtilizados() {
        return repuestosUtilizados;
    }

    public void setRepuestosUtilizados(List<DetalleTrabajoRepuesto> repuestosUtilizados) {
        this.repuestosUtilizados = repuestosUtilizados;
    }

    @Override
    public String toString() {
        return "Trabajo{" +
                "idTrabajo=" + idTrabajo +
                ", idVehiculo=" + idVehiculo +
                ", idServicio=" + idServicio +
                ", fechaRecepcion=" + fechaRecepcion +
                ", fechaEntrega=" + fechaEntrega +
                ", tecnicoAsignado='" + tecnicoAsignado + '\'' +
                ", repuestosUtilizados=" + repuestosUtilizados +
                '}';
    }
}