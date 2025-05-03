package com.carmotors.modelDAO;

import com.carmotors.model.Proveedor;
import com.carmotors.model.Repuesto;
import com.carmotors.util.Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProveedorDAO implements CrudDAO<Proveedor> {
    private Connection con;
    public ProveedorDAO() {
        con = Conexion.getConexion().getConnection();
    }

    @Override
    public boolean agregar(Proveedor proveedor) {
        String sql = "insert into proveedor (nombre, NIT, contacto, frecuencia_visitas) values (?,?,?,?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getNit());
            pstmt.setString(3, proveedor.getContacto());
            pstmt.setInt(4, proveedor.getFrecuenciaVisitas());
            pstmt.executeUpdate();
            return true;  // ← Única línea añadida (retorno exitoso)

        } catch (SQLException e) {
            System.err.println("Error al agregar el proveedor: " + proveedor + "\n" + e.getMessage());
            return false; // ← Única línea añadida (retorno fallido)
        }
    }
}
