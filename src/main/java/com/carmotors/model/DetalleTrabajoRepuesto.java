package com.carmotors.model;

public class DetalleTrabajoRepuesto {
    private Integer idDetalle;
    private Integer idTrabajo;
    private Integer idLote;
    private Integer cantidadUsada;

    public DetalleTrabajoRepuesto() {
    }

    public DetalleTrabajoRepuesto(Integer idDetalle, Integer idLote, Integer idTrabajo, Integer cantidadUsada) {
        this.idDetalle = idDetalle;
        this.idLote = idLote;
        this.idTrabajo = idTrabajo;
        this.cantidadUsada = cantidadUsada;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(Integer idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public Integer getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(Integer cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }

    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }
}
