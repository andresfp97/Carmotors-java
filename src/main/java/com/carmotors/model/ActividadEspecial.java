package com.carmotors.model;

import com.carmotors.model.enums.TipoActividadEspecial;

import java.util.Date;

public class ActividadEspecial {

    private  Integer id;
    private TipoActividadEspecial tipoActividad;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;


    public ActividadEspecial() {

    }

    public ActividadEspecial(Integer id, TipoActividadEspecial tipoActividad, String descripcion, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.tipoActividad = tipoActividad;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoActividadEspecial getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(TipoActividadEspecial tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "ActividadEspecial{" +
                "id=" + id +
                ", tipoActividad=" + tipoActividad +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
