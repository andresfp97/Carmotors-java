package com.carmotors.controller;


import com.carmotors.model.EvaluacionProveedor;
import com.carmotors.modelDAO.EvaluacionProveedorDAO;
import com.carmotors.modelDAO.ProveedorDAO;
import com.carmotors.view.PanelEvaluacionProveedor;


import javax.swing.*;

public class EvaluacionProveedorController {

    private PanelEvaluacionProveedor vista;
    private EvaluacionProveedorDAO evaluacionProveedorDAO;

    private ProveedorDAO proveedorDAO;

    public EvaluacionProveedorController(PanelEvaluacionProveedor vista, EvaluacionProveedorDAO evaluacionProveedorDAO, ProveedorDAO proveedorDAO) {
        this.vista = vista;
        this.evaluacionProveedorDAO = evaluacionProveedorDAO;
        this.proveedorDAO = proveedorDAO;
        this.vista.setGuardarListener(e -> guardarProveedor());
    }

    private void guardarProveedor() {
        try {
            EvaluacionProveedor evaluacionProveedor = vista.getDatosFormulario();
            boolean exito = evaluacionProveedorDAO.agregar(evaluacionProveedor);  // Capturamos el boolean de retorno

            if (exito) {
                JOptionPane.showMessageDialog(vista, "Evaluacion al Proveedor guardada correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar la evaluacion",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarEvaluacionProveedor(EvaluacionProveedor evalucionProveedor) {
        if (evalucionProveedor != null) {
            vista.setDatosFormulario(evalucionProveedor);
        }
    }

    public void limpiarFormulario() {
        vista.limpiarFormulario();
    }
}
