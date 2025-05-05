package com.carmotors.modelDAO;

import com.carmotors.model.Lote;
import com.carmotors.model.enums.EstadoLote;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoteDAO implements CrudDAO<Lote> {
    private final HikariDataSource dataSource;

    public LoteDAO() {
        // Configuración directa de HikariCP
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
    public boolean agregar(Lote lote) {
        String sql = "INSERT INTO lote (id_repuesto, id_proveedor, fecha_ingreso, cantidad_inicial, cantidad_disponible, estado) VALUES (?,?,?,?,?,?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, lote.getIdrepuesto());
            pstmt.setInt(2, lote.getIdproveedor());
            pstmt.setDate(3, lote.getFechaIngreso() != null ?
                    new java.sql.Date(lote.getFechaIngreso().getTime()) : null);
            pstmt.setInt(4, lote.getCantidadInicial());
            pstmt.setInt(5, lote.getCantidadDisponible());
            pstmt.setString(6, lote.getEstado().name());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar el lote: " + e.getMessage());
            return false;
        }
    }

    public List<Lote> obtenerLotesDisponibles() {
        List<Lote> lotes = new ArrayList<>();
        String sql = "SELECT * FROM lote WHERE cantidad_disponible > 0";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lotes.add(mapearLote(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener lotes disponibles: " + e.getMessage());
        }
        return lotes;
    }

    public Lote buscarPorId(Integer idLote) {
        String sql = "SELECT * FROM lote WHERE id_lote = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idLote);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearLote(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar lote por ID: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizarStock(Integer idLote, int nuevaCantidad) {
        String sql = "UPDATE lote SET cantidad_disponible = ?, estado = ? WHERE id_lote = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, nuevaCantidad);
            stmt.setString(2, nuevaCantidad > 0 ? "DISPONIBLE" : "AGOTADO");
            stmt.setInt(3, idLote);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar stock del lote: " + e.getMessage());
            return false;
        }
    }

    private Lote mapearLote(ResultSet rs) throws SQLException {
        Lote lote = new Lote();
        lote.setId(rs.getInt("id_lote"));
        lote.setIdrepuesto(rs.getInt("id_repuesto"));
        lote.setIdproveedor(rs.getInt("id_proveedor"));
        lote.setFechaIngreso(rs.getDate("fecha_ingreso"));
        lote.setCantidadInicial(rs.getInt("cantidad_inicial"));
        lote.setCantidadDisponible(rs.getInt("cantidad_disponible"));
        lote.setEstado(EstadoLote.valueOf(rs.getString("estado")));
        return lote;
    }

    public Lote buscarPorNumeroLote(String numeroLote) {
        try {
            return buscarPorId(Integer.parseInt(numeroLote));
        } catch (NumberFormatException e) {
            System.err.println("Número de lote inválido: " + numeroLote);
            return null;
        }
    }


    public Lote[] obtenerTodos() {
        List<Lote> lotesList = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM lote");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lotesList.add(mapearLote(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los lotes: " + e.getMessage());
        }
        return lotesList.toArray(new Lote[0]);
    }


    public String eliminarPorId(Integer id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement("DELETE FROM lote WHERE id_lote = ?")) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "Lote eliminado correctamente.";
            } else {
                return "No se encontró ningún lote con ID: " + id;
            }

        } catch (SQLException e) {
            // Verificar si la excepción es por violación de clave externa
            if (e.getSQLState().equals("23000")) { // Código estándar para violación de integridad referencial
                return "No se puede eliminar el lote con ID " + id + " porque está referenciado en otra tabla.";
            } else {
                System.err.println("Error al eliminar lote con ID " + id + ": " + e.getMessage());
                e.printStackTrace();
                return "Error al intentar eliminar el lote.";
            }
        }
    }
}