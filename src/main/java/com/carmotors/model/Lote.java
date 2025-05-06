/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmotors.model;

import com.carmotors.model.enums.EstadoLote;

import java.util.Date;

/**
 *
 * @author ANDRES
 */
public class Lote {

    private Integer id;
    private Repuesto Idrepuesto;
    private Proveedor Idproveedor;
    private Date FechaIngreso;
    private Integer cantidadInicial;
    private Integer cantidadDisponible;
    private EstadoLote estado;


    public Lote() {
    }

    public Lote(Integer id, Repuesto idrepuesto, Proveedor idproveedor, Date fechaIngreso, Integer cantidadInicial, Integer cantidadDisponible, EstadoLote estado) {
        this.id = id;
        this.Idrepuesto = idrepuesto;
        this.Idproveedor = idproveedor;
        this.FechaIngreso = fechaIngreso;
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

    public Repuesto getIdrepuesto() {
        return Idrepuesto;
    }

    public void setIdrepuesto(Repuesto idrepuesto) {
        Idrepuesto = idrepuesto;
    }

    public Proveedor getIdproveedor() {
        return Idproveedor;
    }

    public void setIdproveedor(Proveedor idproveedor) {
        Idproveedor = idproveedor;
    }

    public Date getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        FechaIngreso = fechaIngreso;
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
        return "Lote [id=" + id + ", Idrepuesto=" + Idrepuesto + ", Idproveedor=" + Idproveedor + ", FechaIngreso="
                + FechaIngreso + ", cantidadInicial=" + cantidadInicial + ", cantidadDisponible=" + cantidadDisponible
                + ", estado=" + estado + ", getId()=" + getId() + ", getIdrepuesto()=" + getIdrepuesto()
                + ", getIdproveedor()=" + getIdproveedor() + ", getFechaIngreso()=" + getFechaIngreso()
                + ", getCantidadInicial()=" + getCantidadInicial() + ", getCantidadDisponible()="
                + getCantidadDisponible() + ", getEstado()=" + getEstado() + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

   
    
}

