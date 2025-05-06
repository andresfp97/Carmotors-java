package com.carmotors.modelDAO;

import com.carmotors.model.ActividadEspecial;
import com.carmotors.model.enums.TipoActividadEspecial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.carmotors.util.Conexion;

public class ActividadEspecialDAO implements CrudDAO<ActividadEspecial> {

    @Override
    public boolean agregar(ActividadEspecial actividad) {
        String sql = "INSERT INTO actividad_especial (tipo_actividad, descripcion, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, actividad.getTipoActividad().name());
            pstmt.setString(2, actividad.getDescripcion());

            if (actividad.getFechaInicio() != null) {
                pstmt.setDate(3, new Date(actividad.getFechaInicio().getTime()));
            } else {
                pstmt.setNull(3, Types.DATE);
            }

            if (actividad.getFechaFin() != null) {
                pstmt.setDate(4, new Date(actividad.getFechaFin().getTime()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar actividad especial: " + e.getMessage());
            return false;
        }
    }

    public ActividadEspecial obtenerPorId(Integer id) {
        String sql = "SELECT * FROM actividad_especial WHERE id_actividad = ?";
        ActividadEspecial actividad = null;

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    actividad = mapearActividad(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener actividad especial: " + e.getMessage());
        }
        return actividad;
    }

    public List<ActividadEspecial> obtenerTodos() {
        List<ActividadEspecial> actividades = new ArrayList<>();
        String sql = "SELECT * FROM actividad_especial";

        try (Connection con = Conexion.getConexion().getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                actividades.add(mapearActividad(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las actividades especiales: " + e.getMessage());
        }
        return actividades;
    }

    public List<ActividadEspecial> buscarPorId(Integer id) {
        List<ActividadEspecial> actividades = new ArrayList<>();
        String sql = "SELECT * FROM actividad_especial WHERE id_actividad = ?";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    actividades.add(mapearActividad(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar actividad especial por ID: " + e.getMessage());
        }
        return actividades;
    }

    public String eliminarPorId(Integer id) {
        String sql = "DELETE FROM actividad_especial WHERE id_actividad = ?";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "Actividad especial eliminada correctamente.";
            } else {
                return "No se encontró ninguna actividad con ID: " + id;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                return "No se puede eliminar la actividad con ID " + id + " porque está referenciada en otra tabla.";
            } else {
                System.err.println("Error al eliminar actividad especial: " + e.getMessage());
                return "Error al intentar eliminar la actividad especial.";
            }
        }
    }

    public List<ActividadEspecial> buscar(String criterio) {
        List<ActividadEspecial> actividades = new ArrayList<>();
        String sql = "SELECT * FROM actividad_especial WHERE descripcion LIKE ? OR tipo_actividad LIKE ?";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            String parametroBusqueda = "%" + criterio + "%";
            pstmt.setString(1, parametroBusqueda);
            pstmt.setString(2, parametroBusqueda);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    actividades.add(mapearActividad(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar actividades especiales: " + e.getMessage());
        }
        return actividades;
    }

    private ActividadEspecial mapearActividad(ResultSet rs) throws SQLException {
        ActividadEspecial actividad = new ActividadEspecial();
        actividad.setId(rs.getInt("id_actividad"));
        actividad.setTipoActividad(TipoActividadEspecial.valueOf(rs.getString("tipo_actividad")));
        actividad.setDescripcion(rs.getString("descripcion"));
        actividad.setFechaInicio(rs.getDate("fecha_inicio"));
        actividad.setFechaFin(rs.getDate("fecha_fin"));
        return actividad;
    }
}