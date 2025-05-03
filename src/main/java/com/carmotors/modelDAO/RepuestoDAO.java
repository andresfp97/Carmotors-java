/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmotors.modelDAO;

import com.carmotors.model.Repuesto;
import com.carmotors.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ANDRES
 */
public class RepuestoDAO implements CrudDAO<Repuesto> {
    private Connection con;

    public RepuestoDAO() {
        con = Conexion.getConexion().getConnection();
    }

    @Override
    public boolean agregar(Repuesto repuesto) {
        String sql = "insert into repuesto (nombre_repuesto, tipo_repuesto, marca, modelo_compatible, vida_util_estimada) values (?,?,?,?,?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, repuesto.getNombre());
            pstmt.setString(2, repuesto.getTipo().name());
            pstmt.setString(3, repuesto.getMarca());
            pstmt.setString(4, repuesto.getModeloCompatible());

            if (repuesto.getVidaUtilEstimada() != null) {
                pstmt.setDate(5, new Date(repuesto.getVidaUtilEstimada().getTime()));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE);
            }

            pstmt.executeUpdate();
            return true;  // ← Añadido: retorno exitoso

        } catch (SQLException e) {
            System.err.println("Error al agregar el repuesto: " + repuesto + "\n" + e.getMessage());
            return false; // ← Añadido: retorno fallido
        }
    }



    public List<Repuesto> obtenerTodosRepuestosConEstado() throws SQLException {
        String sql = "SELECT *, CASE WHEN vida_util_estimada BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, INTERVAL 3 MONTH) " +
                "THEN 'POR VENCER' ELSE 'DISPONIBLE' END AS estado FROM repuestos ORDER BY vida_util_estimada ASC";

        List<Repuesto> repuestos = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Repuesto repuesto = new Repuesto();
                repuesto.setId(rs.getInt("id"));
                repuesto.setNombre(rs.getString("nombre"));
                repuesto.setVidaUtilEstimada(rs.getDate("vida_util_estimada"));
                // ... otros campos

                // Podrías crear una subclase si necesitas mostrar el estado
                repuestos.add(repuesto);
            }
        }
        return repuestos;
    }
}
