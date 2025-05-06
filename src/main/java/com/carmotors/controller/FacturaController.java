package com.carmotors.controller;

import com.carmotors.model.*;
import com.carmotors.modelDAO.*;
import com.carmotors.util.Conexion;
import com.carmotors.util.FacturaGenerator;
import com.carmotors.view.PanelFactura;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class FacturaController {
    private static final double IVA = 0.19;
    private static final String NIT_EMISOR = "900123456";

    private final PanelFactura vista;
    private final FacturaDAO facturaDAO;
    private final ClienteDAO clienteDAO;
    private final TrabajoDAO trabajoDAO;
    private final VehiculoDAO vehiculoDAO;
    private ActionListener generarFacturaListener;

    public FacturaController(PanelFactura vista, FacturaDAO facturaDAO,
            ClienteDAO clienteDAO, TrabajoDAO trabajoDAO,
            VehiculoDAO vehiculoDAO) {
        if (vista == null || facturaDAO == null ||
                clienteDAO == null || trabajoDAO == null || vehiculoDAO == null) {
            throw new IllegalArgumentException("Dependencias no pueden ser nulas");
        }

        this.vista = vista;
        this.facturaDAO = facturaDAO;
        this.clienteDAO = clienteDAO;
        this.trabajoDAO = trabajoDAO;
        this.vehiculoDAO = vehiculoDAO;
        this.setGenerarFacturaListener(e -> generarFacturaDesdeVista());
        this.vista.setFacturaController(this); // Establecer el controlador en la vista
        cargarFacturas(); // Cargar las facturas iniciales en la tabla
        cargarTrabajosFacturables();

    }

    public void generarFactura(Trabajo trabajo) {
        try {
            if (!validarDatosFactura(trabajo)) {
                return;
            }

            Factura factura = crearFacturaBase(trabajo);

            if (facturaDAO.crearFactura(factura)) {
                crearDetallesFactura(factura, trabajo);
                generarFacturaVisual(factura);
                vista.mostrarMensaje("Factura generada exitosamente");
                vista.limpiarFormulario();
                cargarFacturas(); // Actualizar la tabla de facturas después de generar una nueva
            } else {
                vista.mostrarError("Error al guardar la factura en la base de datos");
            }
        } catch (Exception e) {
            vista.mostrarError("Error al generar factura: " + e.getMessage());
        }
    }

    public void generarFacturaDesdeVista() {
        Trabajo trabajo = vista.getTrabajoSeleccionado();
        if (trabajo == null) {
            vista.mostrarError("Debe seleccionar un trabajo primero");
            return;
        }
        generarFactura(trabajo);
    }

    private boolean validarDatosFactura(Trabajo trabajo) {
        if (trabajo == null) {
            vista.mostrarError("No se ha seleccionado un trabajo válido");
            return false;
        }

        if (trabajo.getVehiculo() == null) {
            vista.mostrarError("El trabajo no tiene un vehículo asociado");
            return false;
        }

        if (trabajo.getServicio() == null) {
            vista.mostrarError("El trabajo no tiene un servicio asociado");
            return false;
        }

        if (trabajo.getVehiculo().getCliente() == null) {
            vista.mostrarError("El vehículo no tiene un cliente asociado");
            return false;
        }

        return true;
    }

    private double calcularSubtotal(Trabajo trabajo) {
        double subtotal = trabajo.getServicio().getCostoManoObra();

        if (trabajo.getRepuestosUtilizados() != null) {
            for (DetalleTrabajoRepuesto detalle : trabajo.getRepuestosUtilizados()) {
                if (detalle != null && detalle.getLote() != null && detalle.getLote().getIdrepuesto() != null) {
                    subtotal += detalle.getLote().getIdrepuesto().getPrecio() * detalle.getCantidadUsada();
                }
            }
        }

        return subtotal;
    }

    private void crearDetallesFactura(Factura factura, Trabajo trabajo) {
        // Detalle para el servicio
        DetalleFactura detalleServicio = new DetalleFactura();
        detalleServicio.setIdFactura(factura.getIdFactura());
        detalleServicio.setConcepto("Servicio");
        detalleServicio.setDescripcion(trabajo.getServicio().getDescripcion());
        detalleServicio.setCantidad(1);
        detalleServicio.setPrecioUnitario(trabajo.getServicio().getCostoManoObra());
        detalleServicio.setSubtotal(trabajo.getServicio().getCostoManoObra());

        // Detalles para repuestos
        if (trabajo.getRepuestosUtilizados() != null) {
            for (DetalleTrabajoRepuesto detalleRepuesto : trabajo.getRepuestosUtilizados()) {
                if (detalleRepuesto != null && detalleRepuesto.getLote() != null
                        && detalleRepuesto.getLote().getIdrepuesto() != null) {

                    DetalleFactura detalle = new DetalleFactura();
                    detalle.setIdFactura(factura.getIdFactura());
                    detalle.setConcepto("Repuesto");
                    detalle.setDescripcion(detalleRepuesto.getLote().getIdrepuesto().getNombre());
                    detalle.setCantidad(detalleRepuesto.getCantidadUsada());
                    detalle.setPrecioUnitario((double) detalleRepuesto.getLote().getIdrepuesto().getPrecio());
                    detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());

                }
            }
        }
        FacturaGenerator.generarFactura(factura);
    }

    private void generarFacturaVisual(Factura factura) {
        try {
            FacturaGenerator.generarFactura(factura);
            vista.mostrarFacturaGenerada(factura);

        } catch (Exception e) {
            vista.mostrarError("Error al generar el archivo de la factura: " + e.getMessage());
        }
    }

    private String generarNumeroFactura() {
        LocalDate hoy = LocalDate.now();
        int consecutivo = obtenerConsecutivoDiario();
        return String.format("FAC-%04d%02d%02d-%06d",
                hoy.getYear(), hoy.getMonthValue(), hoy.getDayOfMonth(),
                consecutivo);
    }

    private String generarCUFE(Trabajo trabajo) {
        double total = calcularSubtotal(trabajo) * (1 + IVA);
        return String.format("%s|%s|%s|%.2f|%s",
                NIT_EMISOR,
                LocalDate.now().toString(),
                generarNumeroFactura(),
                total,
                UUID.randomUUID().toString().substring(0, 10));
    }

    public void cargarTrabajosFacturables() {
        vista.cargarTrabajos(trabajoDAO.obtenerTrabajosParaFacturar());
    }

    /**
     * Establece el listener para generar factura
     * * @param listener El ActionListener a establecer
     */
    public void setGenerarFacturaListener(ActionListener listener) {
        if (listener != null) {
            this.generarFacturaListener = listener;
        }
    }

    private Factura crearFacturaBase(Trabajo trabajo) {
        Factura factura = new Factura();

        // Obtener el cliente completo con todos sus datos
        Cliente cliente = trabajo.getVehiculo().getCliente();
        if (cliente == null || cliente.getNombre() == null) {
            int idCliente = trabajo.getVehiculo().getId();
            cliente = clienteDAO.obtenerPorId(idCliente);
            trabajo.getVehiculo().setCliente(cliente);
        }

        factura.setIdCliente(cliente);
        factura.setIdTrabajo(trabajo);
        factura.setFechaEmision(LocalDate.now());
        factura.setNumeroFactura(generarNumeroFactura());
        factura.setCUFE(generarCUFE(trabajo));

        double subtotal = calcularSubtotal(trabajo);
        factura.setSubtotal(subtotal);
        factura.setImpuestos(subtotal * IVA);
        factura.setTotal(subtotal * (1 + IVA));
        factura.setQrCodigo("QR_" + UUID.randomUUID().toString());

        return factura;
    }

    public int obtenerConsecutivoDiario() {
        int consecutivo = 1;
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "FAC-" + fechaHoy + "-";
        String sql = "SELECT numero_factura FROM factura WHERE numero_factura LIKE ? ORDER BY numero_factura DESC LIMIT 1";
        try (Connection conn = Conexion.getConexion().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "FAC-" + fechaHoy + "-%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String numeroFactura = rs.getString("numero_factura");
                // Extrae la parte numérica después del último guion
                String[] partes = numeroFactura.split("-");
                if (partes.length == 3) {
                    consecutivo = Integer.parseInt(partes[2]) + 1;
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return consecutivo;
    }

    // Método para buscar factura por número de factura
    public void buscarFacturaPorNumero(String numeroFactura) {
        try {
            Factura factura = facturaDAO.obtenerFacturaPorNumero(numeroFactura);
            if (factura != null) {
                List<Factura> listaUnicaFactura = new ArrayList<>();
                listaUnicaFactura.add(factura);
                vista.setFacturas(listaUnicaFactura);
            } else {
                vista.mostrarError("Factura no encontrada");
                cargarFacturas();
            }
        } catch (Exception e) {
            vista.mostrarError("Error al buscar factura: " + e.getMessage());
        }
    }

    // Método para eliminar factura por número de factura
    public void eliminarFacturaPorNumero(String numeroFactura) {
        try {
            String resultado = facturaDAO.eliminarFacturaPorNumero(numeroFactura);
            vista.mostrarMensaje(resultado);
            cargarFacturas(); // Actualizar la tabla después de la eliminación
        } catch (Exception e) {
            vista.mostrarError("Error al eliminar factura: " + e.getMessage());
        }
    }

    // Método para cargar todas las facturas
    public void cargarFacturas() {
        List<Factura> facturas = facturaDAO.obtenerTodos();
        vista.setFacturas(facturas);
    }
}
