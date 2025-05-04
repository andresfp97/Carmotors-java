/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmotors.modelDAO;

import com.carmotors.model.Lote;
import com.carmotors.model.enums.EstadoLote;
import com.carmotors.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


    public List<Lote> obtenerLotesDisponibles() {
        List<Lote> lotes = new ArrayList<>();
        String sql = "SELECT * FROM lote WHERE cantidad_disponible > 0";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Lote lote = new Lote();
                lote.setId(rs.getInt("id_lote"));
                lote.setIdrepuesto(rs.getInt("id_repuesto"));
                lote.setIdproveedor(rs.getInt("id_proveedor"));
                lote.setFechaIngreso(rs.getDate("fecha_ingreso"));
                lote.setCantidadInicial(rs.getInt("cantidad_inicial"));
                lote.setCantidadDisponible(rs.getInt("cantidad_disponible"));
                lotes.add(lote);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener lotes disponibles: " + e.getMessage());
        }
        return lotes;
    }

    /**
     * Busca un lote por su ID
     * @param idLote El ID del lote a buscar
     * @return El objeto Lote encontrado o null si no existe
     */
    public Lote buscarPorId(Integer idLote) {
        String sql = "SELECT * FROM lote WHERE id_lote = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idLote);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearLote(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar lote por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actualiza el stock disponible de un lote
     * @param idLote ID del lote a actualizar
     * @param nuevaCantidad Nueva cantidad disponible
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizarStock(Integer idLote, int nuevaCantidad) {
        String sql = "UPDATE lote SET cantidad_disponible = ?, estado = ? WHERE id_lote = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, nuevaCantidad);

            // Determinar el estado basado en la nueva cantidad
            String estado = nuevaCantidad > 0 ? "DISPONIBLE" : "AGOTADO";
            stmt.setString(2, estado);

            stmt.setInt(3, idLote);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar stock del lote: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todos los lotes con stock disponible
     * @return Lista de lotes disponibles
     */

    /**
     * Mapea un ResultSet a un objeto Lote
     * @param rs ResultSet con los datos del lote
     * @return Objeto Lote mapeado
     * @throws SQLException Si hay error al acceder a los datos
     */
    private Lote mapearLote(ResultSet rs) throws SQLException {
        Lote lote = new Lote();
        lote.setId(rs.getInt("id_lote"));
        lote.setIdrepuesto(rs.getInt("id_repuesto"));
        lote.setIdproveedor(rs.getInt("id_proveedor"));
        lote.setFechaIngreso(rs.getDate("fecha_ingreso"));
        lote.setCantidadInicial(rs.getInt("cantidad_inicial"));
        lote.setCantidadDisponible(rs.getInt("cantidad_disponible"));
        lote.setEstado(EstadoLote.valueOf(rs.getString("estado"))); // Asumiendo que estado es String
        return lote;
    }

    /**
     * Busca un lote por su número de identificación (si existiera ese campo)
     * @param numeroLote El número de lote a buscar
     * @return El objeto Lote encontrado o null si no existe
     */
    public Lote buscarPorNumeroLote(String numeroLote) {
        // Si no tienes un campo numero_lote en tu tabla, este método no sería aplicable
        // Alternativa: buscar por ID si el número de lote es el ID convertido a String
        try {
            Integer idLote = Integer.parseInt(numeroLote);
            return buscarPorId(idLote);
        } catch (NumberFormatException e) {
            System.err.println("Número de lote inválido: " + numeroLote);
            return null;
        }
    }
}
