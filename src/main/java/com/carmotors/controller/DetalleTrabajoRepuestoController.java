package com.carmotors.controller;

import com.carmotors.model.DetalleTrabajoRepuesto;
import com.carmotors.model.Lote;
import com.carmotors.model.Trabajo;
import com.carmotors.modelDAO.DetalleTrabajoRepuestoDAO;
import com.carmotors.modelDAO.LoteDAO;
import com.carmotors.modelDAO.TrabajoDAO;
import com.carmotors.view.PanelDetalleTrabajoRepuesto;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList; // Importar ArrayList

public class DetalleTrabajoRepuestoController {
    private static final Logger LOGGER = Logger.getLogger(DetalleTrabajoRepuestoController.class.getName());
    
    private final PanelDetalleTrabajoRepuesto vista;
    private final DetalleTrabajoRepuestoDAO detalleDAO;
    private final LoteDAO loteDAO;
    private final TrabajoDAO trabajoDAO;
    private Trabajo trabajoActual;
    private Lote loteSeleccionado;
    private Runnable actualizarCallback;

    public DetalleTrabajoRepuestoController(PanelDetalleTrabajoRepuesto vista,
                                          DetalleTrabajoRepuestoDAO detalleDAO,
                                          LoteDAO loteDAO,
                                          TrabajoDAO trabajoDAO) {
        this.vista = Objects.requireNonNull(vista, "La vista no puede ser nula");
        this.detalleDAO = Objects.requireNonNull(detalleDAO, "El DAO de detalles no puede ser nulo");
        this.loteDAO = Objects.requireNonNull(loteDAO, "El DAO de lotes no puede ser nulo");
        this.trabajoDAO = Objects.requireNonNull(trabajoDAO, "El DAO de trabajos no puede ser nulo");

        configurarListeners();
        inicializarVista();
        LOGGER.log(Level.CONFIG, "Controlador de DetalleTrabajoRepuesto inicializado correctamente");
    }

    private void configurarListeners() {
        vista.setGuardarListener(this::guardarDetalle);
        vista.setLoteSelectionListener(this::actualizarStock);
        vista.setEliminarListener(this::eliminarDetalle);
        vista.setBuscarLoteListener(this::buscarLote);
        vista.setTrabajoSelectionListener(this::seleccionarTrabajo);
    }

    private void inicializarVista() {
        try {
            vista.limpiarFormulario();
            cargarTodosLosTrabajos();
            cargarLotesDisponibles();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al inicializar vista", e);
            mostrarError("Error al inicializar: " + e.getMessage());
        }
    }
    
    private void cargarTodosLosTrabajos() {
        LOGGER.log(Level.INFO, "Cargando todos los trabajos");
        try {
            List<Trabajo> trabajos = trabajoDAO.listarTrabajos();
            if (trabajos != null) {
                vista.cargarTrabajos(trabajos);
                LOGGER.log(Level.INFO, "Se cargaron {0} trabajos", trabajos.size());
            } else {
                LOGGER.log(Level.WARNING, "La lista de trabajos es nula");
                mostrarError("No se pudieron cargar los trabajos");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar trabajos", e);
            mostrarError("Error al cargar trabajos: " + e.getMessage());
        }
    }

    private void seleccionarTrabajo(ActionEvent e) {
        try {
            trabajoActual = vista.getTrabajoSeleccionado();
            if (trabajoActual != null) {
                LOGGER.log(Level.INFO, "Trabajo seleccionado: #{0}", trabajoActual.getIdTrabajo());
                cargarDetallesExistentes();
            } else {
                LOGGER.log(Level.INFO, "No se seleccionó ningún trabajo válido");
                vista.mostrarDetalles(new ArrayList<>()); // Pasar lista vacía en lugar de null
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al seleccionar trabajo", ex);
            mostrarError("Error al seleccionar trabajo: " + ex.getMessage());
        }
    }

    private void cargarLotesDisponibles() {
        LOGGER.log(Level.INFO, "Cargando lotes disponibles");
        try {
            List<Lote> lotes = loteDAO.obtenerLotesDisponibles();
            if (lotes != null) {
                vista.cargarLotes(lotes);
                LOGGER.log(Level.INFO, "Se cargaron {0} lotes disponibles", lotes.size());
            } else {
                LOGGER.log(Level.WARNING, "La lista de lotes es nula");
                mostrarError("No se pudieron cargar los lotes");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar lotes disponibles", e);
            mostrarError("Error al cargar lotes: " + e.getMessage());
        }
    }

    private void cargarDetallesExistentes() {
        if (trabajoActual != null) {
            LOGGER.log(Level.INFO, "Cargando detalles para el trabajo #{0}", trabajoActual.getIdTrabajo());
            try {
                List<DetalleTrabajoRepuesto> detalles = detalleDAO.obtenerPorTrabajo(trabajoActual);
                if (detalles != null) {
                    vista.mostrarDetalles(detalles);
                    LOGGER.log(Level.INFO, "Se cargaron {0} detalles para el trabajo #{1}", 
                              new Object[]{detalles.size(), trabajoActual.getIdTrabajo()});
                } else {
                    LOGGER.log(Level.WARNING, "La lista de detalles es nula");
                    vista.mostrarDetalles(new ArrayList<>()); // Pasar lista vacía en lugar de null
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al cargar detalles del trabajo", e);
                mostrarError("Error al cargar detalles: " + e.getMessage());
            }
        }
    }

    private void buscarLote(ActionEvent e) {
        try {
            String numeroLote = vista.getNumeroLote();
            if (numeroLote == null || numeroLote.trim().isEmpty()) {
                mostrarError("Ingrese un número de lote");
                return;
            }

            LOGGER.log(Level.INFO, "Buscando lote con número: {0}", numeroLote);
            loteSeleccionado = loteDAO.buscarPorNumeroLote(numeroLote);
            
            if (loteSeleccionado != null) {
                vista.setStockDisponible(loteSeleccionado.getCantidadDisponible());
                LOGGER.log(Level.INFO, "Lote encontrado: #{0} con stock {1}", 
                          new Object[]{loteSeleccionado.getId(), loteSeleccionado.getCantidadDisponible()});
            } else {
                LOGGER.log(Level.WARNING, "Lote no encontrado: {0}", numeroLote);
                mostrarError("Lote no encontrado");
                vista.setStockDisponible(0);
            }
        } catch (Exception e1) {
            LOGGER.log(Level.SEVERE, "Error al buscar lote", e1);
            mostrarError("Error al buscar lote: " + e1.getMessage());
        }
    }

    private void actualizarStock(ActionEvent e) {
        try {
            loteSeleccionado = vista.getLoteSeleccionado();
            if (loteSeleccionado != null) {
                vista.setStockDisponible(loteSeleccionado.getCantidadDisponible());
                LOGGER.log(Level.INFO, "Stock actualizado para lote #{0}: {1}", 
                          new Object[]{loteSeleccionado.getId(), loteSeleccionado.getCantidadDisponible()});
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al actualizar stock", ex);
            mostrarError("Error al actualizar stock: " + ex.getMessage());
        }
    }

    private void guardarDetalle(ActionEvent e) {
        LOGGER.log(Level.INFO, "Intentando guardar detalle de trabajo-repuesto");
        try {
            if (validarDatos()) {
                DetalleTrabajoRepuesto detalle = crearDetalle();

                if (registrarDetalle(detalle)) {
                    actualizarStockLote();
                    actualizarVista();
                    mostrarMensajeExito("Repuesto registrado correctamente");
                    ejecutarCallbackActualizacion();
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al guardar detalle", ex);
            mostrarError("Error al guardar: " + ex.getMessage());
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

    private boolean registrarDetalle(DetalleTrabajoRepuesto detalle) throws Exception {
        boolean exito = detalleDAO.agregarDetalle(detalle);
        if (!exito) {
            LOGGER.log(Level.WARNING, "No se pudo registrar el detalle");
            mostrarError("Error al registrar el repuesto");
        }
        return exito;
    }

    private void actualizarStockLote() throws Exception {
        int nuevaCantidad = loteSeleccionado.getCantidadDisponible() - vista.getCantidad();
        loteSeleccionado.setCantidadDisponible(nuevaCantidad);
        loteDAO.actualizarStock(loteSeleccionado.getId(), nuevaCantidad);
        LOGGER.log(Level.INFO, "Stock actualizado para lote #{0}: nuevo stock {1}", 
                  new Object[]{loteSeleccionado.getId(), nuevaCantidad});
    }

    private void actualizarVista() {
        try {
            vista.limpiarFormulario();
            cargarDetallesExistentes();
            cargarLotesDisponibles();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar vista", e);
            mostrarError("Error al actualizar vista: " + e.getMessage());
        }
    }

    private void eliminarDetalle(ActionEvent e) {
        try {
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
                    mostrarMensajeExito("Repuesto eliminado correctamente");
                    ejecutarCallbackActualizacion();
                } else {
                    mostrarError("Error al eliminar el repuesto");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar detalle", ex);
            mostrarError("Error al eliminar: " + ex.getMessage());
        }
    }

    private void actualizarStockAlEliminar(DetalleTrabajoRepuesto detalle) throws Exception {
        Lote lote = detalle.getLote();
        int nuevaCantidad = lote.getCantidadDisponible() + detalle.getCantidadUsada();
        lote.setCantidadDisponible(nuevaCantidad);
        loteDAO.actualizarStock(lote.getId(), nuevaCantidad);
        LOGGER.log(Level.INFO, "Stock restaurado para lote #{0}: nuevo stock {1}", 
                  new Object[]{lote.getId(), nuevaCantidad});
    }
    
    private void ejecutarCallbackActualizacion() {
        if (actualizarCallback != null) {
            try {
                LOGGER.log(Level.FINE, "Ejecutando callback de actualización");
                actualizarCallback.run();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al ejecutar callback de actualización", e);
            }
        } else {
            LOGGER.log(Level.WARNING, "No hay callback de actualización configurado");
        }
    }

    private void mostrarError(String mensaje) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                        vista,
                        mensaje,
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                )
        );
    }
    
    private void mostrarMensajeExito(String mensaje) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                        vista,
                        mensaje,
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );
    }
    
    public void setActualizarCallback(Runnable callback) {
        this.actualizarCallback = Objects.requireNonNull(callback, "El callback no puede ser nulo");
        LOGGER.log(Level.CONFIG, "Nuevo callback configurado en DetalleTrabajoRepuestoController");
    }
}

