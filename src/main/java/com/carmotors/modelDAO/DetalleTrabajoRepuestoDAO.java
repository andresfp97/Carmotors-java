package com.carmotors.modelDAO;

import com.carmotors.model.DetalleTrabajoRepuesto;
import com.carmotors.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import com.carmotors.model.DetalleTrabajoRepuesto;
import com.carmotors.model.Lote;
import com.carmotors.model.Trabajo;
import com.carmotors.util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleTrabajoRepuestoDAO {
    private Connection con;

    public DetalleTrabajoRepuestoDAO() {
        this.con = Conexion.getConexion().getConnection();
    }

    // Método para agregar un nuevo detalle
    public boolean agregarDetalle(DetalleTrabajoRepuesto detalle) {
        String sql = "INSERT INTO detalle_trabajo_repuesto (id_trabajo, id_lote, cantidad_usada) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, detalle.getTrabajo().getIdTrabajo());
            pstmt.setInt(2, detalle.getLote().getId());
            pstmt.setInt(3, detalle.getCantidadUsada());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        detalle.setIdDetalle(generatedKeys.getInt(1));
                        actualizarStockLote(detalle.getLote(), detalle.getCantidadUsada());
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al agregar detalle: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar el stock del lote
    private void actualizarStockLote(Lote lote, Integer cantidadUsada) throws SQLException {
        String sql = "UPDATE lote SET cantidad_disponible = cantidad_disponible - ? WHERE id_lote = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, cantidadUsada);
            pstmt.setInt(2, lote.getId());
            pstmt.executeUpdate();
        }
    }

    // Método para obtener detalles por trabajo
    public List<DetalleTrabajoRepuesto> obtenerPorTrabajo(Trabajo trabajo) {
        List<DetalleTrabajoRepuesto> detalles = new ArrayList<>();
        String sql = "SELECT d.*, r.nombre_repuesto, l.numero_lote " +
                "FROM detalle_trabajo_repuesto d " +
                "JOIN lote l ON d.id_lote = l.id_lote " +
                "JOIN repuesto r ON l.id_repuesto = r.id_repuesto " +
                "WHERE d.id_trabajo = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, trabajo.getIdTrabajo());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DetalleTrabajoRepuesto detalle = new DetalleTrabajoRepuesto();
                detalle.setIdDetalle(rs.getInt("id_detalle_trabajo_repuesto"));

                // Configurar el trabajo (solo ID para evitar recursión)
                Trabajo t = new Trabajo();
                t.setIdTrabajo(rs.getInt("id_trabajo"));
                detalle.setTrabajo(t);

                // Configurar el lote con información básica
                Lote lote = new Lote();
                lote.setId(rs.getInt("id_lote"));

                // Aquí podrías agregar más datos del lote si es necesario
                detalle.setLote(lote);

                detalle.setCantidadUsada(rs.getInt("cantidad_usada"));

                detalles.add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalles: " + e.getMessage());
            e.printStackTrace();
        }
        return detalles;
    }

    // Método para eliminar un detalle
    public boolean eliminarDetalle(Integer idDetalle) {
        String sql = "DELETE FROM detalle_trabajo_repuesto WHERE id_detalle_trabajo_repuesto = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idDetalle);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar detalle: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar un detalle
    public boolean actualizarDetalle(DetalleTrabajoRepuesto detalle) {
        String sql = "UPDATE detalle_trabajo_repuesto SET id_lote = ?, cantidad_usada = ? WHERE id_detalle_trabajo_repuesto = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, detalle.getLote().getId());
            pstmt.setInt(2, detalle.getCantidadUsada());
            pstmt.setInt(3, detalle.getIdDetalle());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar detalle: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para verificar si un repuesto ya está en el trabajo
    public boolean existeRepuestoEnTrabajo(Integer idTrabajo, Integer idLote) {
        String sql = "SELECT COUNT(*) FROM detalle_trabajo_repuesto WHERE id_trabajo = ? AND id_lote = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idTrabajo);
            pstmt.setInt(2, idLote);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al verificar repuesto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}