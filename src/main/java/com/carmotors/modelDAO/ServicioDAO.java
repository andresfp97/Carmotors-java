package com.carmotors.modelDAO;

import com.carmotors.model.Servicios;
import com.carmotors.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO implements CrudDAO<Servicios> {
    private Connection con;

    public ServicioDAO() {
        con = Conexion.getConexion().getConnection();
    }

    @Override
    public boolean agregar(Servicios servicio) {
        String sql = "INSERT INTO servicio (tipo_mantenimiento, descripcion, costo_mano_obra, estado_servicio, tiempo_estimado) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, servicio.getTipoMantenimiento().name());
            pstmt.setString(2, servicio.getDescripcion());
            pstmt.setDouble(3, servicio.getCostoManoObra());
            pstmt.setString(4, servicio.getEstado().name());
            pstmt.setInt(5, servicio.getTiempoEstimado());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        servicio.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar servicio: " + e.getMessage());
        }
        return false;
    }


    public boolean actualizar(Servicios servicio) {
        String sql = "UPDATE servicio SET tipo_mantenimiento = ?, descripcion = ?, costo_mano_obra = ?, estado_servicio = ?, tiempo_estimado = ? WHERE id_servicio = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, servicio.getTipoMantenimiento().name());
            pstmt.setString(2, servicio.getDescripcion());
            pstmt.setDouble(3, servicio.getCostoManoObra());
            pstmt.setString(4, servicio.getEstado().name());
            pstmt.setInt(5, servicio.getTiempoEstimado());
            pstmt.setInt(6, servicio.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar servicio: " + e.getMessage());
        }
        return false;
    }


    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM servicio WHERE id_servicio = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar servicio: " + e.getMessage());
        }
        return false;
    }



}