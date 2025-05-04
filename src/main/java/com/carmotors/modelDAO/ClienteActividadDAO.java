package com.carmotors.modelDAO;

import com.carmotors.model.ActividadEspecial;
import com.carmotors.model.ClienteActividad;
import com.carmotors.util.Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteActividadDAO implements CrudDAO<ClienteActividad> {

    @Override
    public boolean agregar(ClienteActividad clienteActividad) {
        String sql = "INSERT INTO cliente_actividad (id_cliente, id_actividad, resultado) VALUES (?,?,?)";

        // Usar try-with-resources para Connection y PreparedStatement
        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // Establecer parámetros
            pstmt.setInt(1, clienteActividad.getIdCliente());
            pstmt.setInt(2, clienteActividad.getIdActividad());
            pstmt.setString(3, clienteActividad.getResultadoActividad().name());

            // Ejecutar y retornar resultado
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar relación cliente-actividad: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }
}
