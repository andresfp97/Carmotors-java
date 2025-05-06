package com.carmotors.controller;

import com.carmotors.model.ActividadEspecial;
import com.carmotors.modelDAO.ActividadEspecialDAO;
import com.carmotors.view.PanelActividadEspecial;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ActividadEspecialController {
    private final PanelActividadEspecial vista;
    private final ActividadEspecialDAO actividadEspecialDAO;
    private final DefaultTableModel tableModel;

    public ActividadEspecialController(PanelActividadEspecial vista, ActividadEspecialDAO actividadEspecialDAO) {
        this.vista = vista;
        this.actividadEspecialDAO = actividadEspecialDAO;
        this.tableModel = vista.getTableModel();

        // Configurar listeners
        this.vista.setGuardarListenerActividad(e -> guardarActividadEspecial());
        this.vista.setBuscarListener(e -> buscarActividades());
        this.vista.setEliminarListener(e -> eliminarActividad());

        // Cargar datos iniciales
        mostrarTodasActividades();
    }

    private void guardarActividadEspecial() {
        try {
            ActividadEspecial actividad = vista.getDatosFormularioActividad();
            boolean exito = actividadEspecialDAO.agregar(actividad);

            if (exito) {
                JOptionPane.showMessageDialog(vista,
                        "Actividad especial guardada correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.limpiarFormularioActividad();
                mostrarTodasActividades();
            } else {
                JOptionPane.showMessageDialog(vista,
                        "No se pudo guardar la actividad especial",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al guardar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarActividades() {
        String criterio = vista.getCriterioBusqueda();
        if (criterio == null || criterio.trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "Ingrese un criterio de búsqueda",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<ActividadEspecial> actividades = actividadEspecialDAO.buscar(criterio);
            if (actividades.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "No se encontraron actividades con ese criterio",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            actualizarTabla(actividades);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al buscar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarActividad() {
        int filaSeleccionada = vista.getTable().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista,
                    "Seleccione una actividad para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(filaSeleccionada, 0);
        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de eliminar esta actividad especial?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            String resultado = actividadEspecialDAO.eliminarPorId(id);
            if (resultado.contains("correctamente")) {
                JOptionPane.showMessageDialog(vista,
                        resultado,
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                mostrarTodasActividades();
            } else {
                JOptionPane.showMessageDialog(vista,
                        resultado,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarTodasActividades() {
        try {
            List<ActividadEspecial> actividades = actividadEspecialDAO.obtenerTodos();
            actualizarTabla(actividades);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al cargar actividades: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla(List<ActividadEspecial> actividades) {
        tableModel.setRowCount(0); // Limpiar tabla

        for (ActividadEspecial actividad : actividades) {
            Object[] fila = {
                    actividad.getId(),
                    actividad.getTipoActividad().toString(),
                    actividad.getDescripcion(),
                    actividad.getFechaInicio(),
                    actividad.getFechaFin()
            };
            tableModel.addRow(fila);
        }
    }


    public void limpiarFormulario() {
        vista.limpiarFormularioActividad();
    }
}