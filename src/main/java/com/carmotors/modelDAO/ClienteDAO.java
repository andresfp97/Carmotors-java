package com.carmotors.modelDAO;

import com.carmotors.model.Cliente;
import com.carmotors.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClienteDAO implements CrudDAO<Cliente> {

    private Connection con;

    public ClienteDAO() {
        con = Conexion.getConexion().getConnection();
    }

    @Override
    public boolean agregar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, identificacion, telefono, correo_electronico) VALUES (?,?,?,?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getIdentificacion());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getCorreoElectronico());

            int filasAfectadas = pstmt.executeUpdate();

            // Obtener el ID generado
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Error al agregar el cliente: " + cliente + "\n" + e.getMessage());
            return false;
        }
    }

    public Cliente obtenerPorId(Integer id) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        Cliente cliente = null;

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setIdentificacion(rs.getString("identificacion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setCorreoElectronico(rs.getString("correo_electronico"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por ID: " + e.getMessage());
        }
        return cliente;
    }

    public List<Cliente> obtenerTodos() {
        System.out.println("Iniciando obtención de clientes...");
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, identificacion FROM cliente";

        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Consulta SQL ejecutada: " + sql);

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setIdentificacion(rs.getString("identificacion"));
                clientes.add(cliente);
                System.out.println("Registro encontrado - ID: " + cliente.getId() +
                        ", Nombre: " + cliente.getNombre());
            }

            System.out.println("Total de registros recuperados: " + clientes.size());
            return clientes;

        } catch (SQLException e) {
            System.err.println("Error en obtenerTodos(): " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void cerrarRecursos(ResultSet rs, Statement st, Connection con) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { /* ignorar */ }
        try { if (st != null) st.close(); } catch (SQLException e) { /* ignorar */ }
        try { if (con != null) con.close(); } catch (SQLException e) { /* ignorar */ }
    }
}
