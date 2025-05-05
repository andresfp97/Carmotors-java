package com.carmotors.model;

public class DetalleFactura {

    private Integer idDetalleFactura;
    private Integer idFactura;
    private String concepto;
    private String descripcion;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

    public DetalleFactura() {
    }
    public DetalleFactura(Integer idDetalleFactura, Integer idFactura, String concepto, String descripcion,
            Integer cantidad, Double precioUnitario, Double subtotal) {
        this.idDetalleFactura = idDetalleFactura;
        this.idFactura = idFactura;
        this.concepto = concepto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }
    public Integer getIdDetalleFactura() {
        return idDetalleFactura;
    }
    public void setIdDetalleFactura(Integer idDetalleFactura) {
        this.idDetalleFactura = idDetalleFactura;
    }
    public Integer getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }
    public String getConcepto() {
        return concepto;
    }
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public Double getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario( Double  precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    public Double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
   
    

}
