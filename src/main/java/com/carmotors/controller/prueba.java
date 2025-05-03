/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmotors.controller;


import com.carmotors.util.Conexion;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author ANDRES
 */
public class prueba {

    public static void main(String[] args) {
       Conexion conexion = Conexion.getConexion();

        if (conexion != null) {
            System.out.println("✅ Conexión establecida correctamente.");
            conexion.cerrarConexion();
            System.out.println("🔒 Conexión cerrada.");
        } else {
            System.out.println("❌ No se pudo establecer la conexión.");
        }

    }
}
