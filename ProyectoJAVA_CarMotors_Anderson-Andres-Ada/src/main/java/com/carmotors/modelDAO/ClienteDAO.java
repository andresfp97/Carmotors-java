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
    public void agregar(Cliente cliente) {

        String sql = "insert into repuesto (nombre, identificacion, telefono, correo_electronico) values (?,?,?,?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getIdentificacion());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getCorreoElectronico());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al agregar el repuesto: " + cliente + "\n" + e.getMessage());
        }
    }
}
