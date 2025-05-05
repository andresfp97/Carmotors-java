package com.carmotors.modelDAO;

import com.carmotors.model.Servicio;
import com.carmotors.model.Trabajo;
import com.carmotors.model.Vehiculo;
import com.carmotors.model.enums.TipoMantenimiento;
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

    private final VehiculoDAO vehiculoDAO;
    private final ServicioDAO servicioDAO;

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
     * 
     * @return Lista de todos los trabajos
     */

    /**
     * Obtiene un trabajo por su ID
     * 
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

    /*
     * public List<Trabajo> listarTrabajos() {
     * List<Trabajo> trabajos = new ArrayList<>();
     * String sql = "SELECT t.*, v.marca, v.modelo, v.placa, " +
     * "s.tipo_mantenimiento, s.descripcion as servicio_descripcion " +
     * "FROM trabajo t " +
     * "LEFT JOIN vehiculo v ON t.id_vehiculo = v.id_vehiculo " +
     * "LEFT JOIN servicio s ON t.id_servicio = s.id_servicio " +
     * "ORDER BY t.fecha_recepcion DESC";
     * 
     * try (Connection con = Conexion.getConexion().getConnection();
     * PreparedStatement pstmt = con.prepareStatement(sql);
     * ResultSet rs = pstmt.executeQuery()) {
     * 
     * while (rs.next()) {
     * Trabajo trabajo = mapearTrabajoConJoins(rs);
     * trabajos.add(trabajo);
     * }
     * } catch (SQLException e) {
     * System.err.println("Error al listar trabajos: " + e.getMessage());
     * e.printStackTrace();
     * }
     * 
     * return trabajos;
     * }
     */
    public List<Trabajo> listarTrabajos() {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.*, v.marca, v.modelo, v.placa, " +
                "s.tipo_mantenimiento, s.descripcion as servicio_descripcion " +
                "FROM trabajo t " +
                "LEFT JOIN vehiculo v ON t.id_vehiculo = v.id_vehiculo " +
                "LEFT JOIN servicio s ON t.id_servicio = s.id_servicio " +
                "ORDER BY t.fecha_recepcion DESC";

        try (Connection con = Conexion.getConexion().getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Trabajo trabajo = mapearTrabajoConJoins(rs);
                trabajos.add(trabajo);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar trabajos: " + e.getMessage());
            e.printStackTrace();
        }

        return trabajos;
    }

    private Trabajo mapearTrabajoConJoins(ResultSet rs) throws SQLException {
        Trabajo trabajo = new Trabajo();
        trabajo.setIdTrabajo(rs.getInt("id_trabajo"));

        // Mapeo optimizado de vehículo
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(rs.getInt("id_vehiculo"));
        vehiculo.setMarca(rs.getString("marca"));
        vehiculo.setModelo(rs.getString("modelo"));
        vehiculo.setPlaca(rs.getString("placa"));
        trabajo.setVehiculo(vehiculo);

        // Mapeo optimizado de servicio
        Servicio servicio = new Servicio();
        servicio.setIdServicio(rs.getInt("id_servicio"));
        servicio.setTipoMantenimiento(TipoMantenimiento.fromString(rs.getString("tipo_mantenimiento")));
        servicio.setDescripcion(rs.getString("servicio_descripcion"));
        trabajo.setServicio(servicio);

        // Fechas
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

    public List<Trabajo> obtenerTrabajosParaFacturar() {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT * FROM trabajo WHERE id_trabajo NOT IN (SELECT id_trabajo FROM factura)";
    
        try (Connection conn = Conexion.getConexion().getConnection();  
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                Trabajo trabajo = new Trabajo();
                trabajo.setIdTrabajo(rs.getInt("id_trabajo"));
    
                java.sql.Date fechaRecepcion = rs.getDate("fecha_recepcion");
                if (fechaRecepcion != null) {
                    trabajo.setFechaRecepcion(fechaRecepcion.toLocalDate());
                }
    
                java.sql.Date fechaEntrega = rs.getDate("fecha_entrega");
                if (fechaEntrega != null) {
                    trabajo.setFechaEntrega(fechaEntrega.toLocalDate());
                }
    
                trabajo.setTecnicoAsignado(rs.getString("tecnico_asignado"));
                trabajo.setServicio(servicioDAO.obtenerPorId(rs.getInt("id_servicio")));
                trabajo.setVehiculo(vehiculoDAO.obtenerPorId(rs.getInt("id_vehiculo")));
    
                trabajos.add(trabajo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trabajos;
    }
}