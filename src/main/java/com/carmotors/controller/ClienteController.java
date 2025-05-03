package com.carmotors.controller;

import com.carmotors.model.Cliente;
import com.carmotors.modelDAO.ClienteDAO;
import com.carmotors.view.PanelCliente;

import javax.swing.*;


public class ClienteController {
    
    private PanelCliente vista;

    private ClienteDAO clienteDAO;

    public ClienteController(PanelCliente vista, ClienteDAO clienteDAO) {
        this.vista = vista;
        this.clienteDAO = clienteDAO;
        this.vista.setGuardarListener(e -> guardarCliente());
    }

    private void guardarCliente() {
        try {
            Cliente cliente = vista.getDatosFormulario();
            boolean guardadoExitoso = clienteDAO.agregar(cliente);  // Capturamos el boolean

            if(guardadoExitoso) {
                JOptionPane.showMessageDialog(vista, "Cliente guardado correctamente");
                vista.limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar el cliente");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage());
        }
    }

    public void cargarCliente(Cliente cliente) {
        if (cliente != null) {
            vista.setDatosFormulario(cliente);
        }
    }

    public void limpiarFormulario() {
        vista.limpiarFormulario();
    }
}
