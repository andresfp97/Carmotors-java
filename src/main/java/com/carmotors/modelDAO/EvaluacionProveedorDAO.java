package com.carmotors.modelDAO;

import com.carmotors.model.EvaluacionProveedor;
import com.carmotors.model.Proveedor;
import com.carmotors.util.Conexion;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluacionProveedorDAO implements CrudDAO<EvaluacionProveedor> {


    public EvaluacionProveedorDAO() {

    }
    @Override
    public boolean agregar(EvaluacionProveedor evaluacionProveedor) {
        String sql = "INSERT INTO evaluacion_proveedor (id_proveedor, fecha_evaluacion, puntualidad, calidad_producto, costo, observaciones) VALUES (?,?,?,?,?,?)";

        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, evaluacionProveedor.getProveedor().getId());
            pstmt.setDate(2, evaluacionProveedor.getFechaEvaluacion() != null ?
                    new java.sql.Date(evaluacionProveedor.getFechaEvaluacion().getTime()) : null);
            pstmt.setInt(3, evaluacionProveedor.getPuntualidad());
            pstmt.setInt(4, evaluacionProveedor.getCalidadProductos());
            pstmt.setInt(5, evaluacionProveedor.getCosto());
            pstmt.setString(6, evaluacionProveedor.getObservaciones());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar la evaluación del proveedor: " + e.getMessage());
            return false;
        }
    }

    public EvaluacionProveedor buscarPorId(Integer idEvaluacion) {
        String sql = "SELECT ep.*, p.nombre AS proveedor_nombre " +
                "FROM evaluacion_proveedor ep " +
                "INNER JOIN proveedor p ON ep.id_proveedor = p.id_proveedor " +
                "WHERE ep.id_evaluacion = ?";
        EvaluacionProveedor evaluacion = null;

        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, idEvaluacion);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    evaluacion = mapearEvaluacionProveedor(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar evaluación por ID: " + e.getMessage());
        }
        return evaluacion;
    }

    public String eliminarPorId(Integer id) {
        String sql = "DELETE FROM evaluacion_proveedor WHERE id_evaluacion = ?";

        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "Evaluación eliminada correctamente.";
            } else {
                return "No se encontró ninguna evaluación con ID: " + id;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar la evaluación: " + e.getMessage());
            return "Error al intentar eliminar la evaluación.";
        }
    }

    public List<EvaluacionProveedor> obtenerTodos() {
        List<EvaluacionProveedor> evaluaciones = new ArrayList<>();
        String sql = "SELECT ep.id_evaluacion, ep.id_proveedor, p.nombre as proveedor_nombre, " +
                "ep.fecha_evaluacion, ep.puntualidad, ep.calidad_producto, " +
                "ep.costo, ep.observaciones " +
                "FROM evaluacion_proveedor ep " +
                "LEFT JOIN proveedor p ON ep.id_proveedor = p.id_proveedor";

        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                evaluaciones.add(mapearEvaluacionProveedor(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener evaluaciones: " + e.getMessage());
            e.printStackTrace();
        }
        return evaluaciones;
    }

    private EvaluacionProveedor mapearEvaluacionProveedor(ResultSet rs) throws SQLException {
        EvaluacionProveedor evaluacion = new EvaluacionProveedor();

        evaluacion.setIdEvaluacion(rs.getInt("id_evaluacion"));

        // Mapear proveedor solo si existe
        if (rs.getObject("id_proveedor") != null) {
            Proveedor proveedor = new Proveedor();
            proveedor.setId(rs.getInt("id_proveedor"));
            proveedor.setNombre(rs.getString("proveedor_nombre"));
            evaluacion.setProveedor(proveedor);
        }

        evaluacion.setFechaEvaluacion(rs.getDate("fecha_evaluacion"));
        evaluacion.setPuntualidad(rs.getInt("puntualidad"));
        evaluacion.calidad_producto(rs.getInt("calidad_producto")); // Ajustado al nombre de la BD
        evaluacion.setCosto(rs.getInt("costo"));
        evaluacion.setObservaciones(rs.getString("observaciones"));

        return evaluacion;
    }

}