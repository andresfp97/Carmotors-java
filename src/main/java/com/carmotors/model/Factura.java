package com.carmotors.model;

import java.time.LocalDate;

public class Factura {
    private Integer idFactura;
    private Cliente cliente;
    private Trabajo trabajo;
    private LocalDate fechaEmision;
    private String numeroFactura;
    private String CUFE;
    private Double subtotal;
    private Double impuestos;
    private Double total;
    private String qrCodigo;

    // Constructores
    public Factura() {
    }

    public Factura(Cliente cliente, Trabajo trabajo, LocalDate fechaEmision,
            String numeroFactura, Double subtotal) {
        this.cliente = cliente;
        this.trabajo = trabajo;
        this.fechaEmision = fechaEmision;
        this.numeroFactura = numeroFactura;
        this.subtotal = subtotal;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public Cliente getIdCliente() {
        return cliente;
    }

    public void setIdCliente(Cliente cliente2) {
        this.cliente = cliente2;
    }

    public Trabajo getIdTrabajo() {
        return trabajo;
    }

    public void setIdTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getCUFE() {
        return CUFE;
    }

    public void setCUFE(String cUFE) {
        CUFE = cUFE;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getQrCodigo() {
        return qrCodigo;
    }

    public void setQrCodigo(String qrCodigo) {
        this.qrCodigo = qrCodigo;
    }

    @Override
    public String toString() {
        return "Factura [idFactura=" + idFactura + ", cliente=" + cliente + ", trabajo=" + trabajo + ", fechaEmision="
                + fechaEmision + ", numeroFactura=" + numeroFactura + ", CUFE=" + CUFE + ", subtotal=" + subtotal
                + ", impuestos=" + impuestos + ", total=" + total + ", qrCodigo=" + qrCodigo + "]";
    }

    public boolean isValid() {
        return cliente.getId() > 0 &&
                trabajo.getId() > 0 &&
                numeroFactura != null && !numeroFactura.isEmpty() &&
                subtotal >= 0 &&
                impuestos >= 0 &&
                total >= 0;
    }

}