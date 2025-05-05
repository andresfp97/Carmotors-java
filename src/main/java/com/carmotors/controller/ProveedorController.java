package com.carmotors.controller;

import com.carmotors.model.Proveedor;
import com.carmotors.modelDAO.ProveedorDAO;
import com.carmotors.view.PanelProveedor;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;

public class ProveedorController {
    private PanelProveedor vista;

    private ProveedorDAO proveedorDAO;
    private JDatePickerImpl datePickerFechaEvaluacion;

    public ProveedorController(PanelProveedor vista, ProveedorDAO proveedorDAO) {
        this.vista = vista;
        this.proveedorDAO = proveedorDAO;
        this.vista.setGuardarListener(e -> guardarProveedor());
    }

    private void guardarProveedor() {
        try {
            Proveedor proveedor = vista.getDatosFormulario();
            boolean exito = proveedorDAO.agregar(proveedor);  // Capturamos el boolean de retorno

            if (exito) {
                JOptionPane.showMessageDialog(vista, "Proveedor guardado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar el proveedor",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
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

