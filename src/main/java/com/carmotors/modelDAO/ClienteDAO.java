package com.carmotors.modelDAO;

import com.carmotors.model.Cliente;
import com.carmotors.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO implements CrudDAO<Cliente>{

    private Connection con;

    public ClienteDAO() {
        con = Conexion.getConexion().getConnection();
    }

    @Override
    public boolean agregar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, identificacion, telefono, correo_electronico) VALUES (?,?,?,?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getIdentificacion());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getCorreoElectronico());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;  // Retorna true si se insertó al menos una fila

        } catch (SQLException e) {
            System.err.println("Error al agregar el cliente: " + cliente + "\n" + e.getMessage());
            return false;
        }
    }
}
