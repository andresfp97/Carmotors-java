package com.carmotors.controller;

import com.carmotors.model.Servicio;
import com.carmotors.modelDAO.ServicioDAO;
import com.carmotors.view.PanelServicio;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class ServicioController {

    private final PanelServicio vista;
    private final ServicioDAO servicioDAO;


     public ServicioController(PanelServicio vista, ServicioDAO servicioDAO) {
        this.vista = Objects.requireNonNull(vista, "La vista no puede ser nula");
        this.servicioDAO = Objects.requireNonNull(servicioDAO, "El DAO de servicios no puede ser nulo");
        
        // Inicializar componentes primero
        vista.inicializarComponente();
        
        // Luego cargar datos
        cargarServiciosIniciales();
        
        this.vista.setGuardarListener(e -> guardarServicio());
    }

    private void cargarServiciosIniciales() {
        SwingWorker<List<Servicio>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Servicio> doInBackground() throws Exception {
                return servicioDAO.listarTodos();
            }

            @Override
            protected void done() {
                try {
                    List<Servicio> servicios = get();
                    if (servicios != null && !servicios.isEmpty()) {
                        vista.mostrarServicios(servicios);
                    } else {
                        JOptionPane.showMessageDialog(vista, 
                            "No se encontraron servicios", 
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(vista, 
                        "Error al cargar servicios: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }


    private void guardarServicio() {
        try {
            Servicio servicio = vista.getDatosFormulario();
            if (servicio == null) {
                JOptionPane.showMessageDialog(vista, "Datos del servicio inválidos");
                return;
            }

            boolean exito = servicioDAO.agregar(servicio);
            if (exito) {
                JOptionPane.showMessageDialog(vista, "Servicio guardado correctamente");
                vista.limpiarFormulario();
                cargarServiciosIniciales(); // Recarga la lista
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar servicio");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}