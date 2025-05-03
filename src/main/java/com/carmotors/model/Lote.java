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
    private Integer Idrepuesto;
    private Integer Idproveedor;
    private Date FechaIngreso;
    private Integer cantidadInicial;
    private Integer cantidadDisponible;
    private EstadoLote estado;


    public Lote() {
    }

    public Lote(Integer id, Integer idrepuesto, Integer idproveedor, Date fechaIngreso, Integer cantidadInicial, Integer cantidadDisponible, EstadoLote estado) {
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

    public Integer getIdrepuesto() {
        return Idrepuesto;
    }

    public void setIdrepuesto(Integer idrepuesto) {
        Idrepuesto = idrepuesto;
    }

    public Integer getIdproveedor() {
        return Idproveedor;
    }

    public void setIdproveedor(Integer idproveedor) {
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
        return "Lote{" +
                "id=" + id +
                ", Idrepuesto=" + Idrepuesto +
                ", Idproveedor=" + Idproveedor +
                ", FechaIngreso=" + FechaIngreso +
                ", cantidadInicial=" + cantidadInicial +
                ", cantidadDisponible=" + cantidadDisponible +
                ", estado=" + estado +
                '}';
    }
}

