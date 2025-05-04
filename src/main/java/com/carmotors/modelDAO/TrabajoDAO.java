package com.carmotors.modelDAO;

import com.carmotors.model.Trabajo;
import com.carmotors.util.Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TrabajoDAO {
    private Connection con;

    public TrabajoDAO() {
        con = Conexion.getConexion().getConnection();
    }

    public boolean crearTrabajo(Trabajo trabajo) {
        String sql = "INSERT INTO trabajo (id_vehiculo, id_servicio, fecha_recepcion, fecha_entrega, tecnico_asignado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // Parámetros obligatorios
            pstmt.setInt(1, trabajo.getIdVehiculo());
            pstmt.setInt(2, trabajo.getIdServicio());
            pstmt.setDate(3, java.sql.Date.valueOf(trabajo.getFechaRecepcion()));

            // Manejo profesional de fecha opcional (NULL)
            if (trabajo.getFechaEntrega() != null) {
                pstmt.setDate(4, java.sql.Date.valueOf(trabajo.getFechaEntrega()));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE); // Especifica explícitamente el tipo
            }

            pstmt.setString(5, trabajo.getTecnicoAsignado());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear trabajo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}