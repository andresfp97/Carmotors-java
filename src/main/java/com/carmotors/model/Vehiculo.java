package com.carmotors.model;

import java.util.List;

public class Vehiculo {
    private Integer id;
    private Cliente cliente;
    private String marca;
    private String modelo;
    private String placa;
    private String tipoVehiculo;
    private List<Trabajo> trabajos;

    // Constructor
    public Vehiculo() {
    }

    public Vehiculo(Integer id, Cliente cliente, String marca, String modelo, String placa, String tipoVehiculo,
            List<Trabajo> trabajos) {
        this.id = id;
        this.cliente = cliente;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.trabajos = trabajos;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public List<Trabajo> getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(List<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }

    @Override
    public String toString() {
        return "Vehiculo [id=" + id + ", cliente=" + cliente + ", marca=" + marca + ", modelo=" + modelo + ", placa="
                + placa + ", tipoVehiculo=" + tipoVehiculo + ", trabajos=" + trabajos + ", getId()=" + getId()
                + ", getCliente()=" + getCliente() + ", getMarca()=" + getMarca() + ", getModelo()=" + getModelo()
                + ", getPlaca()=" + getPlaca() + ", getTipoVehiculo()=" + getTipoVehiculo() + ", getTrabajos()="
                + getTrabajos() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }
}