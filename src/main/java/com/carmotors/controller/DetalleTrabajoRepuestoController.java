package com.carmotors.controller;

import com.carmotors.model.DetalleTrabajoRepuesto;
import com.carmotors.model.Lote;
import com.carmotors.model.Trabajo;
import com.carmotors.modelDAO.DetalleTrabajoRepuestoDAO;
import com.carmotors.modelDAO.LoteDAO;
import com.carmotors.view.PanelDetalleTrabajoRepuesto;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.util.List;

public class DetalleTrabajoRepuestoController {
    private final PanelDetalleTrabajoRepuesto vista;
    private final DetalleTrabajoRepuestoDAO detalleDAO;
    private final LoteDAO loteDAO;
    private Trabajo trabajoActual;
    private Lote loteSeleccionado;

    public DetalleTrabajoRepuestoController(PanelDetalleTrabajoRepuesto vista,
                                            DetalleTrabajoRepuestoDAO detalleDAO,
                                            LoteDAO loteDAO) {
        this.vista = vista;
        this.detalleDAO = detalleDAO;
        this.loteDAO = loteDAO;

        configurarListeners();
        inicializarVista();
    }

    private void configurarListeners() {
        vista.setGuardarListener(this::guardarDetalle);
        vista.setLoteSelectionListener(this::actualizarStock);
        vista.setEliminarListener(this::eliminarDetalle);
        vista.setBuscarLoteListener(this::buscarLote);
    }

    private void inicializarVista() {
        vista.limpiarFormulario();
    }

    public void setTrabajoActual(Trabajo trabajo) {
        this.trabajoActual = trabajo;
        if (trabajo != null) {
            vista.mostrarInfoTrabajo(String.valueOf(trabajo.getIdTrabajo()));
            cargarLotesDisponibles();
            cargarDetallesExistentes();
        } else {
            vista.mostrarInfoTrabajo("N/A");
        }
    }

    private void cargarLotesDisponibles() {
        List<Lote> lotes = loteDAO.obtenerLotesDisponibles();
        vista.cargarLotes(lotes);
    }

    private void cargarDetallesExistentes() {
        if (trabajoActual != null) {
            List<DetalleTrabajoRepuesto> detalles = detalleDAO.obtenerPorTrabajo(trabajoActual);
            vista.mostrarDetalles(detalles);
        }
    }

    private void buscarLote(ActionEvent e) {
        String numeroLote = vista.getNumeroLote();
        if (numeroLote == null || numeroLote.trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un número de lote", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        loteSeleccionado = loteDAO.buscarPorNumeroLote(numeroLote);
        if (loteSeleccionado != null) {
            vista.setStockDisponible(loteSeleccionado.getCantidadDisponible());
        } else {
            JOptionPane.showMessageDialog(vista, "Lote no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            vista.setStockDisponible(0);
        }
    }

    private void actualizarStock(ActionEvent e) {
        loteSeleccionado = vista.getLoteSeleccionado();
        if (loteSeleccionado != null) {
            vista.setStockDisponible(loteSeleccionado.getCantidadDisponible());
        }
    }

    private void guardarDetalle(ActionEvent e) {
        if (validarDatos()) {
            DetalleTrabajoRepuesto detalle = crearDetalle();

            if (registrarDetalle(detalle)) {
                actualizarStockLote();
                actualizarVista();
                JOptionPane.showMessageDialog(vista, "Repuesto registrado correctamente");
            }
        }
    }

    private boolean validarDatos() {
        if (trabajoActual == null) {
            mostrarError("No hay trabajo seleccionado");
            return false;
        }

        if (loteSeleccionado == null) {
            mostrarError("Seleccione un lote válido");
            return false;
        }

        Integer cantidad = vista.getCantidad();
        if (cantidad == null || cantidad <= 0) {
            mostrarError("Ingrese una cantidad válida");
            return false;
        }

        if (cantidad > loteSeleccionado.getCantidadDisponible()) {
            mostrarError("Cantidad excede el stock disponible");
            return false;
        }

        return true;
    }

    private DetalleTrabajoRepuesto crearDetalle() {
        DetalleTrabajoRepuesto detalle = new DetalleTrabajoRepuesto();
        detalle.setTrabajo(trabajoActual);
        detalle.setLote(loteSeleccionado);
        detalle.setCantidadUsada(vista.getCantidad());
        return detalle;
    }

    private boolean registrarDetalle(DetalleTrabajoRepuesto detalle) {
        boolean exito = detalleDAO.agregarDetalle(detalle);
        if (!exito) {
            mostrarError("Error al registrar el repuesto");
        }
        return exito;
    }

    private void actualizarStockLote() {
        int nuevaCantidad = loteSeleccionado.getCantidadDisponible() - vista.getCantidad();
        loteSeleccionado.setCantidadDisponible(nuevaCantidad);
        loteDAO.actualizarStock(loteSeleccionado.getId(), nuevaCantidad);
    }

    private void actualizarVista() {
        vista.limpiarFormulario();
        cargarDetallesExistentes();
        cargarLotesDisponibles();
    }

    private void eliminarDetalle(ActionEvent e) {
        DetalleTrabajoRepuesto detalle = vista.getDetalleSeleccionado();
        if (detalle == null) {
            mostrarError("Seleccione un repuesto para eliminar");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Eliminar este repuesto del trabajo?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (detalleDAO.eliminarDetalle(detalle.getIdDetalle())) {
                actualizarStockAlEliminar(detalle);
                cargarDetallesExistentes();
                JOptionPane.showMessageDialog(vista, "Repuesto eliminado correctamente");
            } else {
                mostrarError("Error al eliminar el repuesto");
            }
        }
    }

    private void actualizarStockAlEliminar(DetalleTrabajoRepuesto detalle) {
        Lote lote = detalle.getLote();
        int nuevaCantidad = lote.getCantidadDisponible() + detalle.getCantidadUsada();
        lote.setCantidadDisponible(nuevaCantidad);
        loteDAO.actualizarStock(lote.getId(), nuevaCantidad);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}