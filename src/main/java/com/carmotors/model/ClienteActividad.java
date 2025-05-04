package com.carmotors.model;

import com.carmotors.model.enums.ResultadoActividad;

public class ClienteActividad {
    private Integer idClienteActividad;
    private Integer idCliente;
    private Integer idActividad;
    private ResultadoActividad resultadoActividad;
    public ClienteActividad() {
    }

    public ClienteActividad(Integer idClienteActividad, Integer idCliente, Integer idActividad, ResultadoActividad resultadoActividad) {
        this.idClienteActividad = idClienteActividad;
        this.idCliente = idCliente;
        this.idActividad = idActividad;
        this.resultadoActividad = resultadoActividad;
    }


    public Integer getIdClienteActividad() {
        return idClienteActividad;
    }

    public void setIdClienteActividad(Integer idClienteActividad) {
        this.idClienteActividad = idClienteActividad;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public ResultadoActividad getResultadoActividad() {
        return resultadoActividad;
    }

    public void setResultadoActividad(ResultadoActividad resultadoActividad) {
        this.resultadoActividad = resultadoActividad;
    }

    @Override
    public String toString() {
        return "ClienteActividad{" +
                "idClienteActividad=" + idClienteActividad +
                ", idCliente=" + idCliente +
                ", idActividad=" + idActividad +
                ", resultadoActividad=" + resultadoActividad +
                '}';
    }
}
