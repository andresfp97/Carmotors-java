package com.carmotors.controller;

import com.carmotors.model.Repuesto;
import com.carmotors.modelDAO.RepuestoDAO;
import com.carmotors.view.PanelRepuesto;
import javax.swing.JOptionPane;

public class RepuestoController {
    private PanelRepuesto vista;
    private RepuestoDAO repuestoDAO;

    public RepuestoController(PanelRepuesto vista, RepuestoDAO repuestoDAO) {
        this.vista = vista;
        this.repuestoDAO = repuestoDAO;
        this.vista.setGuardarListener(e -> guardarRepuesto());
    }

    private void guardarRepuesto() {
        try {
            Repuesto repuesto = vista.getDatosFormulario();
            boolean guardadoExitoso = repuestoDAO.agregar(repuesto);

            if(guardadoExitoso) {
                JOptionPane.showMessageDialog(vista, "Repuesto guardado correctamente");
                vista.limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar el repuesto");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage());
        }
    }


    public void cargarRepuesto(Repuesto repuesto) {
        if (repuesto != null) {
            vista.setDatosFormulario(repuesto);
        }
    }

    public void limpiarFormulario() {
        vista.limpiarFormulario();
    }
}

