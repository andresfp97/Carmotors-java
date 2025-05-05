package com.carmotors.modelDAO;

import com.carmotors.model.Repuesto;
import com.carmotors.model.enums.TipoRepuesto;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepuestoDAO implements CrudDAO<Repuesto> {
    private final HikariDataSource dataSource;

    public RepuestoDAO() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/carmotors");
        config.setUsername("root");
        config.setPassword("root");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(5000);
        config.setIdleTimeout(600000);

        this.dataSource = new HikariDataSource(config);
    }

    @Override
    public boolean agregar(Repuesto repuesto) {
        String sql = "INSERT INTO repuesto (nombre_repuesto, tipo_repuesto, marca, modelo_compatible, vida_util_estimada, precio) VALUES (?,?,?,?,?,?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, repuesto.getNombre());
            pstmt.setString(2, repuesto.getTipo().name());
            pstmt.setString(3, repuesto.getMarca());
            pstmt.setString(4, repuesto.getModeloCompatible());

            if (repuesto.getVidaUtilEstimada() != null) {
                pstmt.setDate(5, new Date(repuesto.getVidaUtilEstimada().getTime()));
            } else {
                pstmt.setNull(5, Types.DATE);
            }

            pstmt.setDouble(6, repuesto.getPrecio());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar repuesto: " + e.getMessage());
            return false;
        }
    }


    public Repuesto[] obtenerTodos() {
        List<Repuesto> repuestos = new ArrayList<>();
        String sql = "SELECT * FROM repuesto";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                repuestos.add(mapearRepuesto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener repuestos: " + e.getMessage());
        }
        return repuestos.toArray(new Repuesto[0]);
    }


    public Repuesto buscarPorId(Integer id) {
        String sql = "SELECT * FROM repuesto WHERE id_repuesto = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRepuesto(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar repuesto por ID: " + e.getMessage());
        }
        return null;
    }


    public String eliminarPorId(Integer id) {
        String sql = "DELETE FROM repuesto WHERE id_repuesto = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "Repuesto eliminado correctamente.";
            } else {
                return "No se encontró ningún repuesto con ID: " + id;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                return "No se puede eliminar el repuesto porque está siendo utilizado en otros registros.";
            }
            System.err.println("Error al eliminar repuesto: " + e.getMessage());
            return "Error al eliminar repuesto: " + e.getMessage();
        }
    }

    private Repuesto mapearRepuesto(ResultSet rs) throws SQLException {
        Repuesto repuesto = new Repuesto();
        repuesto.setId(rs.getInt("id_repuesto"));
        repuesto.setNombre(rs.getString("nombre_repuesto"));
        repuesto.setTipo(TipoRepuesto.valueOf(rs.getString("tipo_repuesto")));
        repuesto.setMarca(rs.getString("marca"));
        repuesto.setModeloCompatible(rs.getString("modelo_compatible"));
        repuesto.setVidaUtilEstimada(rs.getDate("vida_util_estimada"));
        repuesto.setPrecio(rs.getDouble("precio"));
        return repuesto;
    }

    // Método adicional para actualizar un repuesto
    public boolean actualizar(Repuesto repuesto) {
        String sql = "UPDATE repuesto SET nombre_repuesto=?, tipo_repuesto=?, marca=?, modelo_compatible=?, vida_util_estimada=?, precio=? WHERE id_repuesto=?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, repuesto.getNombre());
            pstmt.setString(2, repuesto.getTipo().name());
            pstmt.setString(3, repuesto.getMarca());
            pstmt.setString(4, repuesto.getModeloCompatible());

            if (repuesto.getVidaUtilEstimada() != null) {
                pstmt.setDate(5, new Date(repuesto.getVidaUtilEstimada().getTime()));
            } else {
                pstmt.setNull(5, Types.DATE);
            }

            pstmt.setDouble(6, repuesto.getPrecio());
            pstmt.setInt(7, repuesto.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar repuesto: " + e.getMessage());
            return false;
        }
    }

}

