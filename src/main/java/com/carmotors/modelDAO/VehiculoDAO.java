package com.carmotors.modelDAO;


import com.carmotors.model.Vehiculo;
import com.carmotors.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO implements CrudDAO<Vehiculo> {
    private Connection con;

    public VehiculoDAO() {
        con = Conexion.getConexion().getConnection();
    }

    @Override
    public boolean agregar(Vehiculo vehiculo) {
        String sql = "INSERT INTO vehiculo (id_cliente, marca, modelo, placa, tipo_vehiculo) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, vehiculo.getId());
            pstmt.setString(2, vehiculo.getMarca());
            pstmt.setString(3, vehiculo.getModelo());
            pstmt.setString(4, vehiculo.getPlaca());
            pstmt.setString(5, vehiculo.getTipoVehiculo());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        vehiculo.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar vehículo: " + e.getMessage());
        }
        return false;
    }


    public boolean actualizar(Vehiculo vehiculo) {
        String sql = "UPDATE vehiculo SET id_cliente = ?, marca = ?, modelo = ?, placa = ?, tipo_vehiculo = ? WHERE id_vehiculo = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, vehiculo.getId());
            pstmt.setString(2, vehiculo.getMarca());
            pstmt.setString(3, vehiculo.getModelo());
            pstmt.setString(4, vehiculo.getPlaca());
            pstmt.setString(5, vehiculo.getTipoVehiculo());
            pstmt.setInt(6, vehiculo.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar vehículo: " + e.getMessage());
        }
        return false;
    }


    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
        }
        return false;
    }


    public Vehiculo obtenerPorId(Integer id) {
        String sql = "SELECT * FROM vehiculo WHERE id_vehiculo = ?";
        Vehiculo vehiculo = null;

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                vehiculo = new Vehiculo();
                vehiculo.setId(rs.getInt("id_vehiculo"));
                vehiculo.setId(rs.getInt("id_cliente"));
                vehiculo.setMarca(rs.getString("marca"));
                vehiculo.setModelo(rs.getString("modelo"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setTipoVehiculo(rs.getString("tipo_vehiculo"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener vehículo: " + e.getMessage());
        }
        return vehiculo;
    }


    public List<Vehiculo> obtenerTodos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setId(rs.getInt("id_vehiculo"));
                vehiculo.setId(rs.getInt("id_cliente"));
                vehiculo.setMarca(rs.getString("marca"));
                vehiculo.setModelo(rs.getString("modelo"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setTipoVehiculo(rs.getString("tipo_vehiculo"));
                vehiculos.add(vehiculo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener vehículos: " + e.getMessage());
        }
        return vehiculos;
    }

    public List<Vehiculo> obtenerPorCliente(Integer idCliente) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo WHERE id_cliente = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setId(rs.getInt("id_vehiculo"));
                vehiculo.setId(rs.getInt("id_cliente"));
                vehiculo.setMarca(rs.getString("marca"));
                vehiculo.setModelo(rs.getString("modelo"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setTipoVehiculo(rs.getString("tipo_vehiculo"));
                vehiculos.add(vehiculo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener vehículos por cliente: " + e.getMessage());
        }
        return vehiculos;
    }

    public boolean existePlaca(String placa) {
        String sql = "SELECT COUNT(*) FROM vehiculo WHERE placa = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, placa);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si encuentra alguna coincidencia
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar placa: " + e.getMessage());
        }
        return false;
    }

    public boolean existeVehiculo(int idVehiculo) {
        String sql = "SELECT id_vehiculo FROM vehiculo WHERE id_vehiculo = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idVehiculo);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Retorna true si existe, false si no
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
