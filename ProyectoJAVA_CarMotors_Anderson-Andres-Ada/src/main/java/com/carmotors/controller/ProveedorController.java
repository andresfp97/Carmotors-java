package com.carmotors.controller;

import com.carmotors.model.Proveedor;
import com.carmotors.modelDAO.ProveedorDAO;
import com.carmotors.view.PanelProveedor;
import javax.swing.*;

public class ProveedorController {
    private PanelProveedor vista;

    private ProveedorDAO proveedorDAO;

    public ProveedorController(PanelProveedor vista, ProveedorDAO proveedorDAO) {
        this.vista = vista;
        this.proveedorDAO = proveedorDAO;
        this.vista.setGuardarListener(e -> guardarProveedor());
    }

    private void guardarProveedor() {
        try {
            Proveedor proveedor = vista.getDatosFormulario();
            proveedorDAO.agregar(proveedor);
            JOptionPane.showMessageDialog(vista, "Proveedor guardado correctamente");
            vista.limpiarFormulario();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage());
        }
    }

    public void cargarProveedor(Proveedor proveedor) {
        if (proveedor != null) {
            vista.setDatosFormulario(proveedor);
        }
    }

    public void limpiarFormulario() {
        vista.limpiarFormulario();
    }
}

