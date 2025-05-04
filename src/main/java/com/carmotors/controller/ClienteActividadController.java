package com.carmotors.controller;

import com.carmotors.model.ClienteActividad;
import com.carmotors.modelDAO.ClienteActividadDAO;
import com.carmotors.view.PanelClienteActividad;


import javax.swing.*;

public class ClienteActividadController {
    private ClienteActividadDAO clienteActividadDAO;
    private PanelClienteActividad vista;

    public ClienteActividadController ( PanelClienteActividad vista, ClienteActividadDAO clienteActividadDAO) {
        this.clienteActividadDAO = clienteActividadDAO;
        this.vista = vista;
        this.vista.setGuardarListener(e -> guardarClienteActividad());
    }

    private void guardarClienteActividad() {
        try {
            ClienteActividad clienteActividad = vista.getDatosFormulario();
            boolean exito = clienteActividadDAO.agregar(clienteActividad);  // Capturamos el resultado booleano

            if(exito) {
                JOptionPane.showMessageDialog(vista, "Cliente Actividad guardado correctamente");
                vista.limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar el Cliente en la Actividad");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage());
        }
    }
    public void cargarClienteActividad(ClienteActividad clienteActividad) {
        if (clienteActividad != null) {
            vista.setDatosFormulario(clienteActividad);
        }
    }
}
