package com.carmotors.modelDAO;

import com.carmotors.model.Cliente;
import com.carmotors.model.Factura;
import com.carmotors.model.Servicio;
import com.carmotors.model.Trabajo;
import com.carmotors.model.Vehiculo;
import com.carmotors.util.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FacturaDAO {
    private static final Logger LOGGER = Logger.getLogger(FacturaDAO.class.getName());

    public boolean crearFactura(Factura factura) {
        String sql = "INSERT INTO factura (id_cliente, id_trabajo, fecha_emision, numero_factura, " +
                "CUFE, subtotal, impuestos, total, qr_codigo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, factura.getIdCliente().getId());
            pstmt.setInt(2, factura.getIdTrabajo().getId());
            pstmt.setDate(3, Date.valueOf(factura.getFechaEmision()));
            pstmt.setString(4, factura.getNumeroFactura());
            pstmt.setString(5, factura.getCUFE());
            pstmt.setDouble(6, factura.getSubtotal());
            pstmt.setDouble(7, factura.getImpuestos());
            pstmt.setDouble(8, factura.getTotal());
            pstmt.setString(9, factura.getQrCodigo());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        factura.setIdFactura(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            LOGGER.severe("Error al crear factura: " + e.getMessage());
            return false;
        }
    }

    public List<Factura> obtenerFacturasPorCliente(Integer idCliente) {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM factura WHERE id_cliente = ? ORDER BY fecha_emision DESC";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Factura factura = new Factura();
                factura.setIdFactura(rs.getInt("id_factura"));
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                factura.setIdCliente(cliente);
                Trabajo trabajo = new Trabajo();

                trabajo.setIdTrabajo(rs.getInt("id_trabajo"));

                factura.setIdTrabajo(trabajo);
                factura.setFechaEmision(rs.getDate("fecha_emision").toLocalDate());
                factura.setNumeroFactura(rs.getString("numero_factura"));
                factura.setCUFE(rs.getString("CUFE"));
                factura.setSubtotal(rs.getDouble("subtotal"));
                factura.setImpuestos(rs.getDouble("impuestos"));
                factura.setTotal(rs.getDouble("total"));
                factura.setQrCodigo(rs.getString("qr_codigo"));

                facturas.add(factura);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener facturas: " + e.getMessage());
        }
        return facturas;
    }

    public List<Factura> obtenerTodos() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM factura ORDER BY fecha_emision DESC";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Factura factura = new Factura();
                factura.setIdFactura(rs.getInt("id_factura"));
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                factura.setIdCliente(cliente);
                Trabajo trabajo = new Trabajo();
                trabajo.setIdTrabajo(rs.getInt("id_trabajo"));
                factura.setIdTrabajo(trabajo);
                factura.setFechaEmision(rs.getDate("fecha_emision").toLocalDate());
                factura.setNumeroFactura(rs.getString("numero_factura"));
                factura.setCUFE(rs.getString("CUFE"));
                factura.setSubtotal(rs.getDouble("subtotal"));
                factura.setImpuestos(rs.getDouble("impuestos"));
                factura.setTotal(rs.getDouble("total"));
                factura.setQrCodigo(rs.getString("qr_codigo"));

                facturas.add(factura);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener facturas: " + e.getMessage());
        }
        return facturas;
    }

    public Object obtenerConsecutivoDiario() {
        String sql = "SELECT MAX(numero_factura) FROM factura WHERE DATE(fecha_emision) = CURDATE()";
        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener consecutivo diario: " + e.getMessage());
        }
        return 1;
    }
    /*
     * public List<Trabajo> obtenerTrabajosParaFacturar() {
     * List<Trabajo> trabajos = new ArrayList<>();
     * String sql =
     * "SELECT * FROM trabajo WHERE id_trabajo NOT IN (SELECT id_trabajo FROM factura)"
     * ;
     * 
     * try (Connection con = Conexion.getConexion().getConnection();
     * PreparedStatement pstmt = con.prepareStatement(sql)) {
     * 
     * ResultSet rs = pstmt.executeQuery();
     * while (rs.next()) {
     * Trabajo trabajo = new Trabajo();
     * // Llenar objeto trabajo...
     * trabajos.add(trabajo);
     * }
     * } catch (SQLException e) {
     * LOGGER.severe("Error al obtener trabajos facturables: " + e.getMessage());
     * }
     * return trabajos;
     * }
     */

    public List<Trabajo> obtenerTrabajosParaFacturar() {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.*, v.*, c.*, s.* FROM trabajo t " +
                "LEFT JOIN vehiculo v ON t.id_vehiculo = v.id_vehiculo " +
                "LEFT JOIN cliente c ON v.id_cliente = c.id_cliente " +
                "LEFT JOIN servicio s ON t.id_servicio = s.id_servicio " +
                "WHERE t.id_trabajo NOT IN (SELECT id_trabajo FROM factura)";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Trabajo trabajo = new Trabajo();
                trabajo.setIdTrabajo(rs.getInt("t.id_trabajo"));

                // Configurar vehículo
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setId(rs.getInt("v.id_vehiculo"));
                vehiculo.setPlaca(rs.getString("v.placa"));

                // Configurar cliente
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("c.id_cliente"));
                cliente.setNombre(rs.getString("c.nombre"));
                cliente.setIdentificacion(rs.getString("c.identificacion"));

                vehiculo.setCliente(cliente);
                trabajo.setVehiculo(vehiculo);

                // Configurar servicio
                Servicio servicio = new Servicio();
                servicio.setIdServicio(rs.getInt("s.id_servicio"));
                servicio.setDescripcion(rs.getString("s.descripcion"));
                servicio.setCostoManoObra(rs.getDouble("s.costo_mano_obra"));
                trabajo.setServicio(servicio);

                trabajos.add(trabajo);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener trabajos facturables: " + e.getMessage());
            e.printStackTrace(); // Para debug
        }
        return trabajos;
    }

    public String eliminarFacturaPorNumero(String numeroFactura) {
        String sql = "DELETE FROM factura WHERE numero_factura = ?";
        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, numeroFactura);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0 ? "Factura eliminada exitosamente" : "Factura no encontrada";
        } catch (SQLException e) {
            LOGGER.severe("Error al eliminar factura: " + e.getMessage());
            return "Error al eliminar factura";
        }
    }

    public Factura obtenerFacturaPorNumero(String numeroFactura) {
        String sql = "SELECT * FROM factura WHERE numero_factura = ?";
        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, numeroFactura);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Factura factura = new Factura();
                factura.setIdFactura(rs.getInt("id_factura"));
                factura.setNumeroFactura(rs.getString("numero_factura"));
                factura.setFechaEmision(rs.getDate("fecha_emision").toLocalDate());
                factura.setSubtotal(rs.getDouble("subtotal"));
                factura.setImpuestos(rs.getDouble("impuestos"));
                factura.setTotal(rs.getDouble("total"));
                factura.setQrCodigo(rs.getString("qr_codigo"));
                return factura;
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener factura por número: " + e.getMessage());
        }
        return null;
    }
    
}