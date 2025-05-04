package com.carmotors.modelDAO;

import com.carmotors.model.ActividadEspecial;
import com.carmotors.util.Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActividadEspecialDAO implements CrudDAO<ActividadEspecial>{
    @Override
    public boolean agregar(ActividadEspecial actividadEspecial) {
        String sql = "INSERT INTO actividad_especial (tipo_actividad, descripcion, fecha_inicio, fecha_fin) VALUES (?,?,?,?)";

        // Usar try-with-resources para Connection y PreparedStatement
        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // Establecer parámetros
            pstmt.setString(1, actividadEspecial.getTipoActividad().name());
            pstmt.setString(2, actividadEspecial.getDescripcion());

            // Manejo profesional de fechas NULL
            if (actividadEspecial.getFechaInicio() != null) {
                pstmt.setDate(3, new Date(actividadEspecial.getFechaInicio().getTime()));
            } else {
                pstmt.setNull(3, java.sql.Types.DATE);
            }

            if (actividadEspecial.getFechaFin() != null) {
                pstmt.setDate(4, new Date(actividadEspecial.getFechaFin().getTime()));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }

            // Ejecutar y retornar resultado
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar actividad especial: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
