package com.carmotors.controller;


import com.carmotors.model.EvaluacionProveedor;
import com.carmotors.modelDAO.EvaluacionProveedorDAO;
import com.carmotors.modelDAO.ProveedorDAO;
import com.carmotors.view.PanelEvaluacionProveedor;
import com.carmotors.view.PanelGestionProveedores;


import javax.swing.*;

public class EvaluacionProveedorController {

    private PanelGestionProveedores vista;
    private EvaluacionProveedorDAO evaluacionProveedorDAO;

    private ProveedorDAO proveedorDAO;

    public EvaluacionProveedorController(PanelGestionProveedores vista, EvaluacionProveedorDAO evaluacionProveedorDAO, ProveedorDAO proveedorDAO) {
        this.vista = vista;
        this.evaluacionProveedorDAO = evaluacionProveedorDAO;
        this.proveedorDAO = proveedorDAO;
        this.vista.setGuardarListenerEvaluacion(e -> guardarEvaluacion());
    }

    private void guardarEvaluacion() { // Renombrar el método para mayor claridad
        try {
            EvaluacionProveedor evaluacionProveedor = vista.getDatosFormularioEvaluacion();
            if (evaluacionProveedor != null) { // Verificar si se obtuvo una evaluación válida
                boolean exito = evaluacionProveedorDAO.agregar(evaluacionProveedor);

                if (exito) {
                    JOptionPane.showMessageDialog(vista, "Evaluacion al Proveedor guardada correctamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    vista.limpiarFormulario();

                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo guardar la evaluacion",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Este caso ya debería estar manejado por el JOptionPane en getDatosFormularioEvaluacion(),
                // pero podrías agregar un log o un mensaje adicional aquí si lo deseas.
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarEvaluacionProveedor(EvaluacionProveedor evalucionProveedor) {
        if (evalucionProveedor != null) {
            vista.setDatosFormularioEvaluacion(evalucionProveedor);
        }
    }

    public void limpiarFormulario() {
        vista.limpiarFormulario();
    }
}
