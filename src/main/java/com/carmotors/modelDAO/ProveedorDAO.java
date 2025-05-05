package com.carmotors.modelDAO;

import com.carmotors.model.Proveedor;
import com.carmotors.model.Repuesto;
import com.carmotors.model.Vehiculo;
import com.carmotors.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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



    public Proveedor obtenerPorId(Integer id) {
        String sql = "SELECT * FROM proveedor WHERE id_proveedor = ?";
        Proveedor proveedor = null;

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                proveedor = new Proveedor();
                proveedor.setId(rs.getInt("id_proveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setNit(rs.getString("NIT"));
                proveedor.setContacto(rs.getString("contacto"));
                proveedor.setFrecuenciaVisitas(rs.getInt("frecuencia_visitas"));

            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedor: " + e.getMessage());
        }
        return proveedor;
    }


    public List<Proveedor> obtenerTodos() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                // CORRECCIÓN: Elimina esta línea duplicada y erronea
                // proveedor.setId(rs.getInt("id_vehiculo"));  // ← ESTA LÍNEA ESTÁ MAL
                proveedor.setId(rs.getInt("id_proveedor"));  // Solo esta es correcta
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setNit(rs.getString("NIT"));
                proveedor.setContacto(rs.getString("contacto"));
                proveedor.setFrecuenciaVisitas(rs.getInt("frecuencia_visitas"));

                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedores: " + e.getMessage());
        }
        return proveedores;
    }


}
