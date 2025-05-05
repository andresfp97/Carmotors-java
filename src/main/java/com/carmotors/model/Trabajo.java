package com.carmotors.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Trabajo {
    private int idTrabajo;
    private Vehiculo vehiculo;
    private Servicio servicio;
    private LocalDate fechaRecepcion;
    private LocalDate fechaEntrega;
    private String tecnicoAsignado;
    private List<DetalleTrabajoRepuesto> repuestosUtilizados;
    
    
    public Trabajo() {

    }
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

   
    public int getId() {
        return idTrabajo;
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

    public List<DetalleTrabajoRepuesto> getRepuestosUtilizados() {
        if (repuestosUtilizados == null) {
            System.out.println("[DEBUG] Lista de repuestos es null, inicializando...");
            repuestosUtilizados = new ArrayList<>();
        }
        return repuestosUtilizados;
    }

    public void setRepuestosUtilizados(List<DetalleTrabajoRepuesto> repuestosUtilizados) {
        this.repuestosUtilizados = repuestosUtilizados;
    }
    @Override
    public String toString() {
        return "Trabajo [idTrabajo=" + idTrabajo + ", vehiculo=" + vehiculo + ", servicio=" + servicio
                + ", fechaRecepcion=" + fechaRecepcion + ", fechaEntrega=" + fechaEntrega + ", tecnicoAsignado="
                + tecnicoAsignado + ", repuestosUtilizados=" + repuestosUtilizados + ", getId()=" + getId()
                + ", getIdTrabajo()=" + getIdTrabajo() + ", getVehiculo()=" + getVehiculo() + ", getServicio()="
                + getServicio() + ", getFechaRecepcion()=" + getFechaRecepcion() + ", getFechaEntrega()="
                + getFechaEntrega() + ", getTecnicoAsignado()=" + getTecnicoAsignado() + ", getRepuestosUtilizados()="
                + getRepuestosUtilizados() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }

   

}