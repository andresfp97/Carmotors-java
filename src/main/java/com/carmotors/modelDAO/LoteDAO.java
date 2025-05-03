/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmotors.modelDAO;

import com.carmotors.model.Lote;
import com.carmotors.util.Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ANDRES
 */
public class LoteDAO implements CrudDAO<Lote> {
    private Connection con;

    public LoteDAO() {
        con = Conexion.getConexion().getConnection();
    }

    @Override
    public boolean agregar(Lote lote) {
        String sql = "INSERT INTO lote (id_repuesto, id_proveedor, fecha_ingreso, cantidad_inicial, cantidad_disponible, estado) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, lote.getIdrepuesto());
            pstmt.setInt(2, lote.getIdproveedor());

            if (lote.getFechaIngreso() != null) {
                pstmt.setDate(3, new java.sql.Date(lote.getFechaIngreso().getTime()));
            } else {
                pstmt.setNull(3, java.sql.Types.DATE);
            }

            pstmt.setInt(4, lote.getCantidadInicial());
            pstmt.setInt(5, lote.getCantidadDisponible());
            pstmt.setString(6, lote.getEstado().name());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar el lote: " + lote + "\n" + e.getMessage());
            return false;
        }
    }





}