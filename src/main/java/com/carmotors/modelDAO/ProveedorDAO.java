package com.carmotors.modelDAO;

import com.carmotors.model.Proveedor;
import com.carmotors.model.enums.FrecuenciaVisitas;
import com.carmotors.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO implements CrudDAO<Proveedor> {

    public ProveedorDAO() {
        

    }

    @Override
    public boolean agregar(Proveedor proveedor) {
        String sql = "INSERT INTO proveedor (nombre, nit, contacto, frecuencia_visitas) VALUES (?,?,?,?)";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getNit());
            pstmt.setString(3, proveedor.getContacto());
            pstmt.setString(4, proveedor.getFrecuenciaVisitas().getDescripcion());

            pstmt.executeUpdate();
            return true; // ← Única línea añadida (retorno exitoso)

        } catch (SQLException e) {
            System.err.println("Error al agregar proveedor: " + e.getMessage());
            return false;
        }
    }

    public Proveedor obtenerPorId(Integer id) {
        String sql = "SELECT * FROM proveedor WHERE id_proveedor = ?";
        Proveedor proveedor = null;

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                proveedor = new Proveedor();
                proveedor.setId(rs.getInt("id_proveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setNit(rs.getString("NIT"));
                proveedor.setContacto(rs.getString("contacto"));
                proveedor.setFrecuenciaVisitas(FrecuenciaVisitas.fromString(rs.getString("frecuencia_visitas")));

            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedor: " + e.getMessage());
        }
        return proveedor;
    }

    public List<Proveedor> obtenerTodos() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";

        try (Connection con = Conexion.getConexion().getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                // CORRECCIÓN: Elimina esta línea duplicada y erronea
                // proveedor.setId(rs.getInt("id_vehiculo")); // ← ESTA LÍNEA ESTÁ MAL
                proveedor.setId(rs.getInt("id_proveedor")); // Solo esta es correcta
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setNit(rs.getString("NIT"));
                proveedor.setContacto(rs.getString("contacto"));
                proveedor.setFrecuenciaVisitas(FrecuenciaVisitas.fromString(rs.getString("frecuencia_visitas")));

                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los proveedores: " + e.getMessage());
        }
        return proveedores;
    }

    public List<Proveedor> buscarPorId(Integer id) {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedor WHERE id_proveedor = ?";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    proveedores.add(mapearProveedor(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar proveedor por ID: " + e.getMessage());
        }
        return proveedores;
    }

    public String eliminarPorId(Integer id) {
        String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "Proveedor eliminado correctamente.";
            } else {
                return "No se encontró ningún proveedor con ID: " + id;
            }
        } catch (SQLException e) {
            // Verificar si la excepción es por violación de clave externa
            if (e.getSQLState().equals("23000")) { // Código estándar para violación de integridad referencial
                return "No se puede eliminar el proveedor con ID " + id
                        + " porque está referenciado en otra tabla (evaluaciones o lotes).";
            } else {
                System.err.println("Error al eliminar proveedor: " + e.getMessage());
                return "Error al intentar eliminar el proveedor.";
            }
        }
    }

    private Proveedor mapearProveedor(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(rs.getInt("id_proveedor"));
        proveedor.setNombre(rs.getString("nombre"));
        proveedor.setNit(rs.getString("nit"));
        proveedor.setContacto(rs.getString("contacto"));
        proveedor.setFrecuenciaVisitas(FrecuenciaVisitas.fromString(rs.getString("frecuencia_visitas")));
        return proveedor;
    }
}