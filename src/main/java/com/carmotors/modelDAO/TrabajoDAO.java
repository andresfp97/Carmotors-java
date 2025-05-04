package com.carmotors.modelDAO;

import com.carmotors.model.Trabajo;
import com.carmotors.util.Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrabajoDAO {

    private VehiculoDAO vehiculoDAO;
    private ServicioDAO servicioDAO;

    public TrabajoDAO() {
        this.vehiculoDAO = new VehiculoDAO();
        this.servicioDAO = new ServicioDAO();
    }

    public boolean crearTrabajo(Trabajo trabajo) {
        String sql = "INSERT INTO trabajo (id_vehiculo, id_servicio, fecha_recepcion, fecha_entrega, tecnico_asignado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // Parámetros obligatorios
            pstmt.setInt(1, trabajo.getVehiculo().getId());
            pstmt.setInt(2, trabajo.getServicio().getIdServicio());
            pstmt.setDate(3, java.sql.Date.valueOf(trabajo.getFechaRecepcion()));

            // Manejo profesional de fecha opcional (NULL)
            if (trabajo.getFechaEntrega() != null) {
                pstmt.setDate(4, java.sql.Date.valueOf(trabajo.getFechaEntrega()));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE); // Especifica explícitamente el tipo
            }

            pstmt.setString(5, trabajo.getTecnicoAsignado());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear trabajo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene todos los trabajos registrados en el sistema
     * @return Lista de todos los trabajos
     */
    public List<Trabajo> listarTrabajos() {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT * FROM trabajo ORDER BY fecha_recepcion DESC";
        
        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Trabajo trabajo = new Trabajo();
                trabajo.setIdTrabajo(rs.getInt("id_trabajo"));
                
                trabajo.setVehiculo(vehiculoDAO.obtenerPorId(rs.getInt("id_vehiculo")));
                trabajo.setServicio(servicioDAO.obtenerPorId(rs.getInt("id_servicio")));
                
                // Convertir java.sql.Date a LocalDate
                Date fechaRecepcion = rs.getDate("fecha_recepcion");
                if (fechaRecepcion != null) {
                    trabajo.setFechaRecepcion(fechaRecepcion.toLocalDate());
                }
                
                Date fechaEntrega = rs.getDate("fecha_entrega");
                if (fechaEntrega != null) {
                    trabajo.setFechaEntrega(fechaEntrega.toLocalDate());
                }
                
                trabajo.setTecnicoAsignado(rs.getString("tecnico_asignado"));
                
                trabajos.add(trabajo);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar trabajos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return trabajos;
    }

    /**
     * Obtiene un trabajo por su ID
     * @param idTrabajo ID del trabajo a buscar
     * @return Objeto Trabajo encontrado o null si no existe
     */
    public Trabajo obtenerPorId(int idTrabajo) {
        String sql = "SELECT * FROM trabajo WHERE id_trabajo = ?";
        
        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setInt(1, idTrabajo);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Trabajo trabajo = new Trabajo();
                    trabajo.setIdTrabajo(rs.getInt("id_trabajo"));
                    
                    trabajo.setVehiculo(vehiculoDAO.obtenerPorId(rs.getInt("id_vehiculo")));
                    trabajo.setServicio(servicioDAO.obtenerPorId(rs.getInt("id_servicio")));
                    
                    // Convertir java.sql.Date a LocalDate
                    Date fechaRecepcion = rs.getDate("fecha_recepcion");
                    if (fechaRecepcion != null) {
                        trabajo.setFechaRecepcion(fechaRecepcion.toLocalDate());
                    }
                    
                    Date fechaEntrega = rs.getDate("fecha_entrega");
                    if (fechaEntrega != null) {
                        trabajo.setFechaEntrega(fechaEntrega.toLocalDate());
                    }
                    
                    trabajo.setTecnicoAsignado(rs.getString("tecnico_asignado"));
                    
                    return trabajo;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener trabajo por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}