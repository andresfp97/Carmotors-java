package com.carmotors.modelDAO;

import com.carmotors.model.EvaluacionProveedor;
import com.carmotors.model.Proveedor;
import com.carmotors.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EvaluacionProveedorDAO implements CrudDAO<EvaluacionProveedor> {

    private Connection con;

    public EvaluacionProveedorDAO() {
        con = Conexion.getConexion().getConnection();
    }

    @Override
    public boolean agregar(EvaluacionProveedor evaluacionProveedor) {
        String sql = "INSERT INTO evaluacion_proveedor (id_proveedor, fecha_evaluacion, puntualidad, calidad_producto, costo,observaciones) VALUES (?, ?, ?, ?, ?,?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, evaluacionProveedor.getProveedor().getId());
            if (evaluacionProveedor.getFechaEvaluacion() != null) {
                pstmt.setDate(2, new java.sql.Date(evaluacionProveedor.getFechaEvaluacion().getTime()));
            } else {
                pstmt.setNull(2, java.sql.Types.DATE);
            }

            pstmt.setInt(3, evaluacionProveedor.getPuntualidad());
            pstmt.setInt(4, evaluacionProveedor.getCalidadProductos());
            pstmt.setInt(5, evaluacionProveedor.getCosto());
            pstmt.setString(6, evaluacionProveedor.getObservaciones());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        evaluacionProveedor.setIdEvaluacion(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al  crear Evaluacion al proveedor: " + e.getMessage());
        }
        return false;
    }

    public EvaluacionProveedor obtenerPorId(Integer id) {
        String sql = "SELECT * FROM evaluacion_proveedor WHERE id_evaluacion = ?";
        EvaluacionProveedor evaluacionProveedor = null;

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                evaluacionProveedor = new EvaluacionProveedor();
                evaluacionProveedor.setIdEvaluacion(rs.getInt("id_evaluacion"));
                evaluacionProveedor.setIdEvaluacion(rs.getInt("id_proveedor"));
                evaluacionProveedor.setFechaEvaluacion(rs.getDate("fecha_evaluacion"));
                evaluacionProveedor.setPuntualidad(rs.getInt("puntualidad"));
                evaluacionProveedor.setCalidadProductos(rs.getInt("calidad_producto"));
                evaluacionProveedor.setCalidadProductos(rs.getInt("costo"));
                evaluacionProveedor.setObservaciones(rs.getString("observaciones"));

            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la evaluacion del proveedor: " + e.getMessage());
        }
        return evaluacionProveedor;
    }

    public List<EvaluacionProveedor> obtenerTodos() {
        List<EvaluacionProveedor> evaluaciones = new ArrayList<>();
        String sql = "SELECT * FROM evaluacion_proveedor"; // Asegúrate que el nombre de la tabla sea correcto

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                EvaluacionProveedor evaluacion = new EvaluacionProveedor();
                evaluacion.setIdEvaluacion(rs.getInt("id_evaluacion"));

                // Crear objeto Proveedor con solo el ID (luego se completa si es necesario)
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt("id_proveedor"));
                evaluacion.setProveedor(proveedor);

                evaluacion.setFechaEvaluacion(rs.getDate("fecha_evaluacion"));
                evaluacion.setPuntualidad(rs.getInt("puntualidad"));
                evaluacion.setCalidadProductos(rs.getInt("calidad_producto"));
                evaluacion.setCosto(rs.getInt("costo"));
                evaluacion.setObservaciones(rs.getString("observaciones"));

                evaluaciones.add(evaluacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener evaluaciones: " + e.getMessage());
        }
        return evaluaciones;
    }
}


