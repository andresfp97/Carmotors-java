package com.carmotors.modelDAO;

import com.carmotors.model.Servicio;
import com.carmotors.model.enums.EstadoServicio;
import com.carmotors.model.enums.TipoMantenimiento;
import com.carmotors.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {
    private Connection con;

    public ServicioDAO() {
        con = Conexion.getConexion().getConnection();
    }

    public boolean agregar(Servicio servicio) {
        String sql = "INSERT INTO servicio (tipo_mantenimiento, descripcion, costo_mano_obra, estado_servicio, tiempo_estimado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // Los índices deben ser secuenciales y coincidir con los parámetros en la consulta SQL
            pstmt.setString(1, servicio.getTipoMantenimiento().name());  // Primer parámetro (tipo_mantenimiento)
            pstmt.setString(2, servicio.getDescripcion());               // Segundo parámetro (descripcion)
            pstmt.setDouble(3, servicio.getCostoManoObra());             // Tercer parámetro (costo_mano_obra)
            pstmt.setString(4, servicio.getEstadoServicio().name());      // Cuarto parámetro (estado_servicio)
            pstmt.setInt(5, servicio.getTiempoEstimadoMinutos());        // Quinto parámetro (tiempo_estimado)

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar servicio: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Servicio obtenerPorId(int idServicio) {
        String sql = "SELECT id_servicio, tipo_mantenimiento, descripcion, costo_mano_obra, estado_servicio, tiempo_estimado FROM servicio WHERE id_servicio = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idServicio);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Servicio(
                            rs.getInt("id_servicio"),
                            TipoMantenimiento.fromString(rs.getString("tipo_mantenimiento")),
                            rs.getString("descripcion"),
                            rs.getDouble("costo_mano_obra"),
                            EstadoServicio.fromString(rs.getString("estado_servicio")), // Conversión
                            rs.getInt("tiempo_estimado")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Servicio> obtenerTodos() {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT id_servicio, tipo_mantenimiento, descripcion, costo_mano_obra, estado_servicio, tiempo_estimado FROM servicio";

        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                try {
                    Servicio servicio = new Servicio(
                            rs.getInt("id_servicio"),
                            TipoMantenimiento.fromString(rs.getString("tipo_mantenimiento")),
                            rs.getString("descripcion"),
                            rs.getDouble("costo_mano_obra"),
                            EstadoServicio.fromString(rs.getString("estado_servicio")),
                            rs.getInt("tiempo_estimado")
                    );
                    servicios.add(servicio);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error al mapear servicio: " + e.getMessage());
                    // Opcional: continuar con el siguiente registro
                    continue;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener servicios: " + e.getMessage());
            e.printStackTrace();
            // Opcional: puedes lanzar una excepción personalizada aquí
            // throw new DataAccessException("Error al obtener servicios", e);
        }

        return servicios;
    }
}