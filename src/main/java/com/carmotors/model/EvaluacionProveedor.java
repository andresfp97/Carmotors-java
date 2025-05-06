package com.carmotors.model;

import java.util.Date;

public class EvaluacionProveedor {

    private Integer idEvaluacion;
    private Proveedor proveedor;
    private Date fechaEvaluacion;
    private Integer puntualidad;
    private Integer calidadProductos;
    private Integer costo;
    private String observaciones;

    public EvaluacionProveedor() {
    }

    public EvaluacionProveedor( Proveedor proveedor, Date fechaEvaluacion,
                                Integer puntualidad, Integer calidadProductos,
                                Integer costo, String observaciones) {
        this.proveedor = proveedor;
        this.fechaEvaluacion = fechaEvaluacion;
        this.puntualidad = puntualidad;
        this.calidadProductos = calidadProductos;
        this.costo = costo;
        this.observaciones = observaciones;
    }

    public Integer getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Integer idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Date getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(Date fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public Integer getPuntualidad() {
        return puntualidad;
    }

    public void setPuntualidad(Integer puntualidad) {
        this.puntualidad = puntualidad;
    }

    public Integer getCalidadProductos() {
        return calidadProductos;
    }

    public void calidad_producto(Integer calidadProductos) {
        this.calidadProductos = calidadProductos;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "EvalucionProveedor{" +
                "idEvaluacion=" + idEvaluacion +
                ", proveedor=" + proveedor +
                ", fechaEvaluacion=" + fechaEvaluacion +
                ", puntualidad=" + puntualidad +
                ", calidadProductos=" + calidadProductos +
                ", costo=" + costo +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }

    public void setCalidadProductos(int int1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCalidadProductos'");
    }
}
