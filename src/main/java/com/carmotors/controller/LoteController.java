package com.carmotors.controller;

import com.carmotors.model.Lote;
import com.carmotors.modelDAO.LoteDAO;
import com.carmotors.view.PanelRepuesto;

import javax.swing.*;

public class LoteController {

    private LoteDAO loteDAO;
    private PanelRepuesto vista;

    public LoteController( PanelRepuesto vista, LoteDAO loteDAO) {
        this.loteDAO = loteDAO;
        this.vista = vista;
        this.vista.setGuardarListenerLote(e -> guardarLote());
    }

    private void guardarLote() {
        try {
            Lote lote = vista.getDatosFormularioLote();
            boolean exito = loteDAO.agregar(lote);  // Capturamos el resultado booleano

            if(exito) {
                JOptionPane.showMessageDialog(vista, "Lote guardado correctamente");
                vista.limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar el lote");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage());
        }
    }
    public void cargarLote(Lote lote) {
        if (lote != null) {
            vista.setDatosFormularioLote(lote);
        }
    }


}
