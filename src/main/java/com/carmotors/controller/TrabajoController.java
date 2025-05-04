package com.carmotors.controller;

import com.carmotors.model.Servicio;
import com.carmotors.model.Trabajo;
import com.carmotors.model.Vehiculo;
import com.carmotors.modelDAO.TrabajoDAO;
import com.carmotors.modelDAO.VehiculoDAO;
import com.carmotors.modelDAO.ServicioDAO;
import com.carmotors.view.PanelTrabajo;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class TrabajoController {
    private final PanelTrabajo vista;
    private final TrabajoDAO trabajoDAO;
    private final VehiculoDAO vehiculoDAO;
    private final ServicioDAO servicioDAO;

    public TrabajoController(PanelTrabajo vista, TrabajoDAO trabajoDAO,
                             VehiculoDAO vehiculoDAO, ServicioDAO servicioDAO) {
        this.vista = vista;
        this.trabajoDAO = trabajoDAO;
        this.vehiculoDAO = vehiculoDAO;
        this.servicioDAO = servicioDAO;

        cargarDatosIniciales();
        this.vista.setCrearTrabajoListener(e -> crearTrabajo());
    }

    private void cargarDatosIniciales() {
        // Verifica que los DAOs están devolviendo datos
        List<Vehiculo> vehiculos = vehiculoDAO.obtenerTodos();
        List<Servicio> servicios = servicioDAO.obtenerTodos();

        System.out.println("Vehículos cargados: " + vehiculos.size());
        System.out.println("Servicios cargados: " + servicios.size());

        vista.cargarVehiculos(vehiculos);
        vista.cargarServicios(servicios);
    }

    private void crearTrabajo() {
        try {
            // Validar campos obligatorios
            if (vista.getVehiculoSeleccionado() == null ||
                    vista.getServicioSeleccionado() == null ||
                    vista.getTecnico().isEmpty()) {

                JOptionPane.showMessageDialog(vista,
                        "Debe seleccionar vehículo, servicio y técnico",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear objeto trabajo
            Trabajo trabajo = new Trabajo();
            trabajo.setIdVehiculo(vista.getVehiculoSeleccionado().getId());
            trabajo.setIdServicio(vista.getServicioSeleccionado().getIdServicio());
            trabajo.setFechaRecepcion(LocalDate.now());

            // Manejo opcional de fecha de entrega
            if (vista.getFechaEntrega() != null) {
                trabajo.setFechaEntrega(vista.getFechaEntrega());
            } // Si es null, se mantiene null

            trabajo.setTecnicoAsignado(vista.getTecnico());

            // Insertar en BD
            if (trabajoDAO.crearTrabajo(trabajo)) {
                JOptionPane.showMessageDialog(vista, "Trabajo creado exitosamente");
                vista.limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al crear trabajo");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}