package com.carmotors.controller;

import com.carmotors.model.ClienteActividad;
import com.carmotors.modelDAO.ClienteActividadDAO;
import com.carmotors.view.PanelActividadEspecial;



import javax.swing.*;

public class ClienteActividadController {
    private ClienteActividadDAO clienteActividadDAO;
    private PanelActividadEspecial vista;

    public ClienteActividadController ( PanelActividadEspecial vista, ClienteActividadDAO clienteActividadDAO) {
        this.clienteActividadDAO = clienteActividadDAO;
        this.vista = vista;
        this.vista.setGuardarListenerClienteActividad(e -> guardarClienteActividad());
    }

    private void guardarClienteActividad() {
        try {
            ClienteActividad clienteActividad = vista.getDatosFormularioClienteActividad();
            boolean exito = clienteActividadDAO.agregar(clienteActividad);  // Capturamos el resultado booleano

            if(exito) {
                JOptionPane.showMessageDialog(vista, "Cliente Actividad guardado correctamente");
                vista.limpiarFormularioClienteActividad();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar el Cliente en la Actividad");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage());
        }
    }
    public void cargarClienteActividad(ClienteActividad clienteActividad) {
        if (clienteActividad != null) {
            vista.setDatosFormularioClienteActividad(clienteActividad);
        }
    }
}
