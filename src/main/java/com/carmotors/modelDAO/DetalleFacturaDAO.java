package com.carmotors.modelDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.carmotors.model.DetalleFactura;
import com.carmotors.model.DetalleTrabajoRepuesto;
import com.carmotors.util.Conexion;

public class DetalleFacturaDAO {

    public boolean agregar(DetalleFactura detalleFactura) {
        String sql = "insert into detalle_factura (id_factura, concepto, descripcion, cantidad, precio_unitario, subtotal) values (?,?,?,?,?,?)";
        try (Connection con = new Conexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, detalleFactura.getIdFactura());
            pstmt.setString(2, detalleFactura.getConcepto());
            pstmt.setString(3, detalleFactura.getDescripcion());
            pstmt.setInt(4, detalleFactura.getCantidad());
            pstmt.setDouble(5, detalleFactura.getPrecioUnitario());
            pstmt.setDouble(6, detalleFactura.getSubtotal());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<DetalleFactura> obtenerTodos() {
        List<DetalleFactura> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle_factura";
        try (Connection con = new Conexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
    
            while (rs.next()) {
                DetalleFactura detalle = new DetalleFactura();
                detalle.setIdDetalleFactura(rs.getInt("id_detalle_factura"));
                detalle.setIdFactura(rs.getInt("id_factura"));
                detalle.setConcepto(rs.getString("concepto"));
                detalle.setDescripcion(rs.getString("descripcion"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                detalle.setSubtotal(rs.getDouble("subtotal"));
                detalles.add(detalle);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }
    

    public List<DetalleTrabajoRepuesto> obtenerPorTrabajo(int idTrabajo) {
        System.out.println("=== DEBUG OBTENER REPUESTOS PARA TRABAJO " + idTrabajo + " ===");
        List<DetalleTrabajoRepuesto> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle_trabajo_repuesto WHERE id_trabajo = ?";

        try (Connection con = new Conexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, idTrabajo);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
                DetalleTrabajoRepuesto detalle = new DetalleTrabajoRepuesto();
                // Mapear campos...
                detalles.add(detalle);
            }
            System.out.println("Repuestos encontrados: " + count);
        } catch (SQLException e) {
            System.err.println("Error al obtener repuestos: " + e.getMessage());
        }
        return detalles;
    }

}
