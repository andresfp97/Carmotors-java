package com.carmotors.model;

public class DetalleTrabajoRepuesto {
    private Integer idDetalle;
    private Trabajo trabajo;  // Objeto Trabajo en lugar de solo ID
    private Lote lote;       // Objeto Lote en lugar de solo ID
    private Integer cantidadUsada;

    public DetalleTrabajoRepuesto() {
    }

    public DetalleTrabajoRepuesto(Trabajo trabajo, Lote lote, Integer cantidadUsada) {
        this.trabajo = trabajo;
        this.lote = lote;
        this.cantidadUsada = cantidadUsada;
    }

    // Getters y Setters
    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Integer getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(Integer cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }

    @Override
    public String toString() {
        return "DetalleTrabajoRepuesto{" +
                "idDetalle=" + idDetalle +
                ", trabajo=" + trabajo.getIdTrabajo() +
                ", lote=" + lote.getId() +
                ", cantidadUsada=" + cantidadUsada +
                '}';
    }
}