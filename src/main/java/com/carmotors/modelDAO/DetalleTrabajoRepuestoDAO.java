package com.carmotors.modelDAO;

import com.carmotors.model.DetalleTrabajoRepuesto;
import com.carmotors.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleTrabajoRepuestoDAO {
    private Connection con;

    public DetalleTrabajoRepuestoDAO() {
        con = Conexion.getConexion().getConnection();
    }

    /**
     * Registra el uso de un repuesto (lote) en un trabajo específico
     */
    public boolean registrarUsoRepuesto(int idTrabajo, int idLote, int cantidadUsada) {
        String sql = "INSERT INTO detalle_trabajo_repuesto (id_trabajo, id_lote, cantidad_usada) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idTrabajo);
            pstmt.setInt(2, idLote);
            pstmt.setInt(3, cantidadUsada);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar uso de repuesto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los repuestos utilizados en un trabajo específico
     */
    public List<DetalleTrabajoRepuesto> obtenerRepuestosPorTrabajo(int idTrabajo) {
        List<DetalleTrabajoRepuesto> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle_trabajo_repuesto WHERE id_trabajo = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idTrabajo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DetalleTrabajoRepuesto detalle = new DetalleTrabajoRepuesto();
                detalle.setIdDetalle(rs.getInt("id_detalle_trabajo_repuesto"));
                detalle.setIdTrabajo(rs.getInt("id_trabajo"));
                detalle.setIdLote(rs.getInt("id_lote"));
                detalle.setCantidadUsada(rs.getInt("cantidad_usada"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener repuestos por trabajo: " + e.getMessage());
        }

        return detalles;
    }

    /**
     * Obtiene el historial de uso de un lote específico
     */
    public List<DetalleTrabajoRepuesto> obtenerHistorialPorLote(int idLote) {
        List<DetalleTrabajoRepuesto> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle_trabajo_repuesto WHERE id_lote = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idLote);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DetalleTrabajoRepuesto detalle = new DetalleTrabajoRepuesto();
                detalle.setIdDetalle(rs.getInt("id_detalle_trabajo_repuesto"));
                detalle.setIdTrabajo(rs.getInt("id_trabajo"));
                detalle.setIdLote(rs.getInt("id_lote"));
                detalle.setCantidadUsada(rs.getInt("cantidad_usada"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial por lote: " + e.getMessage());
        }

        return detalles;
    }
}