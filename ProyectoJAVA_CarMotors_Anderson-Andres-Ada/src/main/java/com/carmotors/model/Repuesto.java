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
public class Repuesto {
    
    private Integer id;
    private String nombre;
    private String tipo;
    private String marca;
    private String modeloCompatible;
    private Date vidaUtilEstimada;

    public Repuesto() {
    }

    public Repuesto(Integer id, String nombre, String tipo, String marca, String modeloCompatible, Date vidaUtilEstimada) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marca = marca;
        this.modeloCompatible = modeloCompatible;
        this.vidaUtilEstimada = vidaUtilEstimada;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
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

    @Override
    public String toString() {
        return "Repuesto{" + "id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", marca=" + marca + ", modeloCompatible=" + modeloCompatible + ", vidaUtilEstimada=" + vidaUtilEstimada + '}';
    }
  
    
    
}
