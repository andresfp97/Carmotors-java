
package com.carmotors.model;

import com.carmotors.model.enums.FrecuenciaVisitas;

/**
 *
 * @author ANDRES
 */
public class Proveedor {
    private Integer id;
    private String nombre;
    private String nit;
    private String contacto;
    private FrecuenciaVisitas frecuenciaVisitas;   

    public Proveedor() {
    }

    public Proveedor(Integer id, String nombre, String nit, String coctacto, FrecuenciaVisitas frecuenciaVisitas) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.contacto = coctacto;
        this.frecuenciaVisitas = frecuenciaVisitas;
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String coctacto) {
        this.contacto = coctacto;
    }

    public FrecuenciaVisitas getFrecuenciaVisitas() {
        return frecuenciaVisitas;
    }

    public void setFrecuenciaVisitas(FrecuenciaVisitas frecuenciaVisitas) {
        this.frecuenciaVisitas = frecuenciaVisitas;
    }

    @Override
    public String toString() {
        return "Proveedor [id=" + id + ", nombre=" + nombre + ", nit=" + nit + ", contacto=" + contacto
                + ", frecuenciaVisitas=" + frecuenciaVisitas + ", getId()=" + getId() + ", getNombre()=" + getNombre()
                + ", getNit()=" + getNit() + ", getContacto()=" + getContacto() + ", getFrecuenciaVisitas()="
                + getFrecuenciaVisitas() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }


    
    
}
