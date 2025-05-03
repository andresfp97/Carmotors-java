/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.carmotors.modelDAO;

import com.carmotors.model.Repuesto;


/**
 *
 * @author ANDRES
 */
public interface CrudDAO<T> {

     boolean agregar(T entidad);

}
