package com.carmotors.model;

import java.time.LocalDate;

public class Trabajo {
    private int idTrabajo;
    private int idVehiculo;
    private int idServicio;
    private LocalDate fechaRecepcion;
    private LocalDate fechaEntrega;
    private String tecnicoAsignado;

    // Constructor
    public Trabajo(int idTrabajo, int idVehiculo, int idServicio,
                   LocalDate fechaRecepcion, LocalDate fechaEntrega,
                   String tecnicoAsignado) {
        this.idTrabajo = idTrabajo;
        this.idVehiculo = idVehiculo;
        this.idServicio = idServicio;
        this.fechaRecepcion = fechaRecepcion;
        this.fechaEntrega = fechaEntrega;
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public Trabajo() {

    }

    // Getters y Setters
    public int getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(int idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public LocalDate getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setTecnicoAsignado(String tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
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
                '}';
    }
}