package com.carmotors.modelDAO;

import com.carmotors.model.DetalleTrabajoRepuesto;
import com.carmotors.model.Lote;
import com.carmotors.model.Repuesto;
import com.carmotors.model.Trabajo;
import com.carmotors.util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class DetalleTrabajoRepuestoDAO {
    private static final Logger LOGGER = Logger.getLogger(DetalleTrabajoRepuestoDAO.class.getName());
    
    private LoteDAO loteDAO;
    private TrabajoDAO trabajoDAO;

    public DetalleTrabajoRepuestoDAO() {
        this.loteDAO = new LoteDAO();
        this.trabajoDAO = new TrabajoDAO();
    }

    // Método para agregar un nuevo detalle
    public boolean agregarDetalle(DetalleTrabajoRepuesto detalle) {
    Connection con = null;
    try {
        con = Conexion.getConexion().getConnection();
        con.setAutoCommit(false); // Iniciar transacción

        // 1. Validar datos antes de insertar
        if (detalle.getTrabajo() == null || detalle.getLote() == null || detalle.getCantidadUsada() == null || 
            detalle.getCantidadUsada() <= 0) {
            throw new IllegalArgumentException("Datos del detalle inválidos");
        }

        // 2. Verificar stock disponible
        if (!verificarStockDisponible(con, detalle.getLote().getId(), detalle.getCantidadUsada())) {
            throw new SQLException("No hay suficiente stock disponible");
        }

        // 3. Insertar detalle
        String sql = "INSERT INTO detalle_trabajo_repuesto (id_trabajo, id_lote, cantidad_usada) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, detalle.getTrabajo().getIdTrabajo());
            pstmt.setInt(2, detalle.getLote().getId());
            pstmt.setInt(3, detalle.getCantidadUsada());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo insertar el detalle");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    detalle.setIdDetalle(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("No se obtuvo ID generado");
                }
            }
        }

        // 4. Actualizar stock
        actualizarStockLote(con, detalle.getLote(), detalle.getCantidadUsada());

        con.commit(); // Confirmar transacción
        LOGGER.log(Level.INFO, "Detalle agregado exitosamente con ID: {0}", detalle.getIdDetalle());
        return true;

    } catch (Exception e) {
        if (con != null) {
            try {
                con.rollback(); // Revertir en caso de error
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error al hacer rollback", ex);
            }
        }
        LOGGER.log(Level.SEVERE, "Error al agregar detalle: " + e.getMessage(), e);
        JOptionPane.showMessageDialog(null, 
            "Error al guardar detalle: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    } finally {
        if (con != null) {
            try {
                con.setAutoCommit(true); // Restaurar auto-commit
                con.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar conexión", e);
            }
        }
    }
}


    // Método para actualizar el stock del lote
    private boolean verificarStockDisponible(Connection con, int idLote, int cantidad) throws SQLException {
        String sql = "SELECT cantidad_disponible FROM lote WHERE id_lote = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idLote);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidad_disponible") >= cantidad;
                }
                return false;
            }
        }
    }
    
    private void actualizarStockLote(Connection con, Lote lote, int cantidadUsada) throws SQLException {
        String sql = "UPDATE lote SET cantidad_disponible = cantidad_disponible - ? WHERE id_lote = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, cantidadUsada);
            pstmt.setInt(2, lote.getId());
            int affected = pstmt.executeUpdate();
            if (affected == 0) {
                throw new SQLException("No se pudo actualizar el stock del lote");
            }
            LOGGER.log(Level.INFO, "Stock actualizado para lote #{0}", lote.getId());
        }
    }
    // Método para obtener detalles por trabajo
    public List<DetalleTrabajoRepuesto> obtenerPorTrabajo(Trabajo trabajo) {
        List<DetalleTrabajoRepuesto> detalles = new ArrayList<>();
        String sql = "SELECT d.*, l.id_lote, l.cantidad_disponible, l.id_repuesto " +
                "FROM detalle_trabajo_repuesto d " +
                "JOIN lote l ON d.id_lote = l.id_lote " +
                "WHERE d.id_trabajo = ?";

        try (
            Connection con = Conexion.getConexion().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, trabajo.getIdTrabajo());
            LOGGER.log(Level.INFO, "Consultando detalles para trabajo #{0}", trabajo.getIdTrabajo());
            
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DetalleTrabajoRepuesto detalle = new DetalleTrabajoRepuesto();
                detalle.setIdDetalle(rs.getInt("id_detalle_trabajo_repuesto"));

                // Usar el trabajo pasado como parámetro
                detalle.setTrabajo(trabajo);

                // Obtener el lote completo con sus relaciones
                int idLote = rs.getInt("id_lote");
                Lote lote = loteDAO.buscarPorId(idLote);
                detalle.setLote(lote);

                detalle.setCantidadUsada(rs.getInt("cantidad_usada"));
                detalles.add(detalle);
                
                LOGGER.log(Level.FINE, "Detalle cargado: ID {0}, Lote #{1}, Cantidad {2}", 
                          new Object[]{detalle.getIdDetalle(), idLote, detalle.getCantidadUsada()});
            }
            
            LOGGER.log(Level.INFO, "Se encontraron {0} detalles para el trabajo #{1}", 
                      new Object[]{detalles.size(), trabajo.getIdTrabajo()});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener detalles: {0}", e.getMessage());
            e.printStackTrace();
        }
        return detalles;
    }

    // Método para eliminar un detalle
    public boolean eliminarDetalle(Integer idDetalle) {
        // Primero obtenemos el detalle para saber qué lote y cantidad restaurar
        DetalleTrabajoRepuesto detalle = obtenerDetallePorId(idDetalle);
        if (detalle == null) {
            LOGGER.log(Level.WARNING, "No se puede eliminar: detalle #{0} no encontrado", idDetalle);
            return false;
        }
        
        String sql = "DELETE FROM detalle_trabajo_repuesto WHERE id_detalle_trabajo_repuesto = ?";

        try (
            Connection con = Conexion.getConexion().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idDetalle);
            LOGGER.log(Level.INFO, "Eliminando detalle #{0}", idDetalle);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                LOGGER.log(Level.INFO, "Detalle #{0} eliminado exitosamente", idDetalle);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No se pudo eliminar el detalle #{0}", idDetalle);
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar detalle #{0}: {1}", new Object[]{idDetalle, e.getMessage()});
            e.printStackTrace();
            return false;
        }
    }
    
    // Método para obtener un detalle por su ID
    public DetalleTrabajoRepuesto obtenerDetallePorId(Integer idDetalle) {
        String sql = "SELECT * FROM detalle_trabajo_repuesto WHERE id_detalle_trabajo_repuesto = ?";
        
        try (
            Connection con = Conexion.getConexion().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idDetalle);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                DetalleTrabajoRepuesto detalle = new DetalleTrabajoRepuesto();
                detalle.setIdDetalle(rs.getInt("id_detalle_trabajo_repuesto"));
                
                int idTrabajo = rs.getInt("id_trabajo");
                Trabajo trabajo = trabajoDAO.obtenerPorId(idTrabajo);
                detalle.setTrabajo(trabajo);
                
                int idLote = rs.getInt("id_lote");
                Lote lote = loteDAO.buscarPorId(idLote);
                detalle.setLote(lote);
                
                detalle.setCantidadUsada(rs.getInt("cantidad_usada"));
                
                return detalle;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener detalle por ID: {0}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Método para actualizar un detalle
    public boolean actualizarDetalle(DetalleTrabajoRepuesto detalle) {
        String sql = "UPDATE detalle_trabajo_repuesto SET id_lote = ?, cantidad_usada = ? WHERE id_detalle_trabajo_repuesto = ?";

        try (
            Connection con = Conexion.getConexion().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, detalle.getLote().getId());
            pstmt.setInt(2, detalle.getCantidadUsada());
            pstmt.setInt(3, detalle.getIdDetalle());

            LOGGER.log(Level.INFO, "Actualizando detalle #{0}: Lote #{1}, Cantidad {2}", 
                      new Object[]{detalle.getIdDetalle(), detalle.getLote().getId(), detalle.getCantidadUsada()});
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                LOGGER.log(Level.INFO, "Detalle #{0} actualizado exitosamente", detalle.getIdDetalle());
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No se pudo actualizar el detalle #{0}", detalle.getIdDetalle());
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar detalle: {0}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para verificar si un repuesto ya está en el trabajo
    public boolean existeRepuestoEnTrabajo(Integer idTrabajo, Integer idLote) {
        String sql = "SELECT COUNT(*) FROM detalle_trabajo_repuesto WHERE id_trabajo = ? AND id_lote = ?";

        try (
            Connection con = Conexion.getConexion().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idTrabajo);
            pstmt.setInt(2, idLote);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                boolean existe = rs.getInt(1) > 0;
                LOGGER.log(Level.INFO, "Verificación de repuesto en trabajo: Trabajo #{0}, Lote #{1}, Existe: {2}", 
                          new Object[]{idTrabajo, idLote, existe});
                return existe;
            }
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al verificar repuesto: {0}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}