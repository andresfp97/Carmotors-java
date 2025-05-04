package com.carmotors.controller;

import com.carmotors.model.Servicio;
import com.carmotors.modelDAO.ServicioDAO;
import com.carmotors.view.PanelServicio;

import javax.swing.*;
import java.util.List;

public class ServicioController {

        private final PanelServicio vista;
        private final ServicioDAO servicioDAO;

        public ServicioController(PanelServicio vista, ServicioDAO servicioDAO) {
            this.vista = vista;
            this.servicioDAO = servicioDAO;
            this.vista.setGuardarListener(e -> guardarServicio());
        }

    private void guardarServicio() {
        try {
            Servicio servicio = vista.getDatosFormulario();
            if (servicioDAO.agregar(servicio)) {
                JOptionPane.showMessageDialog(vista, "Servicio guardado correctamente");
                vista.limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar servicio");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage());
        }
    }
}