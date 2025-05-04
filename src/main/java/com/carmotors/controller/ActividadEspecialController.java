package com.carmotors.controller;



import com.carmotors.model.ActividadEspecial;
import com.carmotors.modelDAO.ActividadEspecialDAO;
import com.carmotors.view.PanelActividadEspecial;
import com.carmotors.view.PanelRepuesto;

import javax.swing.*;

public class ActividadEspecialController {

    private PanelActividadEspecial vista;
    private ActividadEspecialDAO actividadEspeciaDAO;

    public ActividadEspecialController(PanelActividadEspecial vista, ActividadEspecialDAO actividadEspeciaDAO) {
        this.vista = vista;
        this.actividadEspeciaDAO = actividadEspeciaDAO;
        this.vista.setGuardarListener(e -> guardarActividadEspecial());
    }

    private void guardarActividadEspecial() {
        try {
            ActividadEspecial actividadEspecial = vista.getDatosFormulario();
            boolean guardadoExitoso = actividadEspeciaDAO.agregar(actividadEspecial);

            if(guardadoExitoso) {
                JOptionPane.showMessageDialog(vista, "Actividad Especial guardado correctamente");
                vista.limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar la actividad");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage());
        }
    }


    public void cargarActividadEspecial(ActividadEspecial actividadEspecial) {
        if (actividadEspecial != null) {
            vista.setDatosFormulario(actividadEspecial);
        }
    }

    public void limpiarFormulario() {
        vista.limpiarFormulario();
    }
}
