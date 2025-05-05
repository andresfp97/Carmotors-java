package com.carmotors.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Trabajo {
    private int idTrabajo;
    private Vehiculo vehiculo;
    private Servicio servicio;
    private LocalDate fechaRecepcion;
    private LocalDate fechaEntrega;
    private String tecnicoAsignado;
    

    // Constructor
    public Trabajo(int idTrabajo, Vehiculo vehiculo, Servicio servicio,
                   LocalDate fechaRecepcion, LocalDate fechaEntrega,
                   String tecnicoAsignado) {
        this.idTrabajo = idTrabajo;
        this.vehiculo = vehiculo;
        this.servicio = servicio;
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

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
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
        String fecha = fechaRecepcion != null ? 
                      fechaRecepcion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : 
                      "Sin fecha";
        return "Trabajo #" + idTrabajo + " - " + fecha;
    }
}