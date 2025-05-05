/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmotors.model;

import com.carmotors.model.enums.TipoRepuesto;

import java.util.Date;

/**
 *
 * @author ANDRES
 */
public class Repuesto {
    
    private Integer id;
    private String nombre;
    private TipoRepuesto tipo;
    private String marca;
    private String modeloCompatible;
    private Date vidaUtilEstimada;
    private Integer precio;

    public Repuesto() {
    }

    public Repuesto(Integer id, String nombre, TipoRepuesto tipo, String marca, String modeloCompatible, Date vidaUtilEstimada, Integer precio) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marca = marca;
        this.modeloCompatible = modeloCompatible;
        this.vidaUtilEstimada = vidaUtilEstimada;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoRepuesto getTipo() {
        return tipo;
    }

    public void setTipo(TipoRepuesto tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModeloCompatible() {
        return modeloCompatible;
    }

    public void setModeloCompatible(String modeloCompatible) {
        this.modeloCompatible = modeloCompatible;
    }

    public Date getVidaUtilEstimada() {
        return vidaUtilEstimada;
    }

    public void setVidaUtilEstimada(Date vidaUtilEstimada) {
        this.vidaUtilEstimada = vidaUtilEstimada;
    }

    public boolean estaPorVencer() {
        if (vidaUtilEstimada == null) return false;

        Date ahora = new Date();
        Date tresMesesDespues = new Date(ahora.getTime() + (90L * 24 * 60 * 60 * 1000)); // 90 días

        return !vidaUtilEstimada.after(tresMesesDespues) && !vidaUtilEstimada.before(ahora);
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Repuesto [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", marca=" + marca
                + ", modeloCompatible=" + modeloCompatible + ", vidaUtilEstimada=" + vidaUtilEstimada + ", precio="
                + precio + "]";
    }

    

    
}
