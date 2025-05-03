/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmotors.model;

import java.util.Date;

/**
 *
 * @author ANDRES
 */
public class Lote {
    
    private Integer id;
    private Repuesto repuesto;
    private Proveedor proveedor;
    private Date FechaIngreso;
    private Integer cantidadInicial;
    private Integer cantidadDisponible;
    private EstadoLote estado;  

    public Lote() {
    }

    public Lote(Integer id, Repuesto repuesto, Proveedor proveedor, Date FechaIngreso, Integer cantidadInicial, Integer cantidadDisponible, EstadoLote estado) {
        this.id = id;
        this.repuesto = repuesto;
        this.proveedor = proveedor;
        this.FechaIngreso = FechaIngreso;
        this.cantidadInicial = cantidadInicial;
        this.cantidadDisponible = cantidadDisponible;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Repuesto getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(Repuesto repuesto) {
        this.repuesto = repuesto;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Date getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(Date FechaIngreso) {
        this.FechaIngreso = FechaIngreso;
    }

    public Integer getCantidadInicial() {
        return cantidadInicial;
    }

    public void setCantidadInicial(Integer cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public EstadoLote getEstado() {
        return estado;
    }

    public void setEstado(EstadoLote estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Lote{" + "id=" + id + ", repuesto=" + repuesto + ", proveedor=" + proveedor + ", FechaIngreso=" + FechaIngreso + ", cantidadInicial=" + cantidadInicial + ", cantidadDisponible=" + cantidadDisponible + ", estado=" + estado + '}';
    }
      
    
}
